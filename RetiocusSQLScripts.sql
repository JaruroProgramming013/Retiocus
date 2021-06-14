CREATE   PROCEDURE AnhadirChat
@uidSolicitante VARCHAR(128),
@uidSolicitado VARCHAR(128)
AS
    BEGIN
        IF @uidSolicitado>@uidSolicitante

            INSERT INTO Chat (FechaCreacion, uid_usuario_1, uid_usuario_2)
            VALUES (GETDATE(),@uidSolicitante,@uidSolicitado)

        ELSE IF @uidSolicitado<@uidSolicitante

            INSERT INTO Chat (FechaCreacion, uid_usuario_1, uid_usuario_2)
            VALUES (GETDATE(),@uidSolicitado,@uidSolicitante)

        ELSE

            THROW 51002,'No se puede hacer un chat con la misma persona.',1
    END
go

CREATE   PROCEDURE AnhadirPreferencia
@uidUsuario VARCHAR(128),
@idtema INT
AS
    BEGIN
        IF NOT EXISTS(SELECT uid_usuario,IDTema
                      FROM Preferencia
                      WHERE uid_usuario=@uidUsuario AND IDTema=@idtema)
            INSERT INTO Preferencia VALUES (@uidUsuario,@idtema)
        ELSE
            THROW 51004,'Este usuario ya tiene una preferencia por el tema especificado.',1
    END
go

CREATE   PROCEDURE AnhadirSugerencia
@uidSugestor VARCHAR(128),
@NombreTema VARCHAR(30),
@motivos VARCHAR(200)
AS
    BEGIN
        IF NOT EXISTS (SELECT nombre
                       FROM Tema
                       WHERE @NombreTema=nombre)
            BEGIN
                IF NOT EXISTS(SELECT * FROM Sugerencia)

                BEGIN
                    DBCC CHECKIDENT ('Sugerencia', RESEED, 1);

                    SET IDENTITY_INSERT Sugerencia ON

                    INSERT INTO Sugerencia (ID, uid_sugestor, nombreTema, motivos, aceptada)

                    VALUES (1,@uidSugestor,@NombreTema,@motivos,NULL)

                    SET IDENTITY_INSERT Sugerencia OFF
                END

                ELSE

                    INSERT INTO Sugerencia (uid_sugestor, nombreTema, motivos, aceptada)

                    VALUES (@uidSugestor,@NombreTema,@motivos,NULL)
            END
        ELSE
        THROW 51004,'El tema sugerido ya esta en la base de datos.',1;
    END
go

CREATE   PROCEDURE AnhadirTema
@nombre VARCHAR(30)
AS
    BEGIN
        IF NOT EXISTS(SELECT * FROM Tema)
            DBCC CHECKIDENT ('Tema', RESEED, 1);

        INSERT INTO Tema (nombre)
        VALUES (@nombre)
    END
go

CREATE   PROC AnhadirUsuario
@uidUsuario VARCHAR(128)
AS
    BEGIN
        INSERT INTO Usuario (firebase_uid) VALUES
        (@uidUsuario)
    END
go

CREATE   PROCEDURE AsignarAdministrador
@uidUsuario VARCHAR(128)
AS
    BEGIN
        UPDATE Usuario
        SET esAdmin=1
        WHERE firebase_uid=@uidUsuario
    END
go

CREATE   PROCEDURE BorrarPreferencia @uidSolicitante VARCHAR(128), @idTema INT AS
    BEGIN
        DELETE FROM Preferencia
        WHERE uid_usuario=@uidSolicitante
        AND IDTema=@idTema
    END
go

CREATE   FUNCTION ChatsDeUsuario(@uidSolicitante VARCHAR(128)) RETURNS TABLE
AS RETURN
SELECT ID, uid_usuario_1, uid_usuario_2
FROM Chat
WHERE (uid_usuario_1=@uidSolicitante AND uid_usuario_2!=@uidSolicitante) OR (uid_usuario_1!=@uidSolicitante AND uid_usuario_2=@uidSolicitante)
go

CREATE   PROCEDURE CrearMensaje @idChat INT, @uidRemitente VARCHAR(128), @cuerpo VARCHAR(500), @fechaEnvio DATETIME
AS
BEGIN
    INSERT INTO Mensaje (ChatID, Remitente, Cuerpo, FechaHoraEnvio)
    VALUES (@idChat,@uidRemitente,@cuerpo, @fechaEnvio)
END
go

CREATE   PROCEDURE EliminarChat
@uidSolicitante VARCHAR(128),
@uidSolicitado VARCHAR(128)
AS
    BEGIN
        IF @uidSolicitado>@uidSolicitante

            DELETE FROM Chat
            WHERE uid_usuario_1=@uidSolicitante AND uid_usuario_2=@uidSolicitado

        ELSE IF @uidSolicitado<@uidSolicitante

            DELETE FROM Chat
            WHERE uid_usuario_1=@uidSolicitado AND uid_usuario_2=@uidSolicitante

    END
go

CREATE   PROCEDURE EvaluarSugerencia
@IDSugerencia INT,
@decision BIT
AS
    BEGIN
            UPDATE Sugerencia
            SET aceptada=@decision
            WHERE @IDSugerencia=ID

            /*
            No se puede hacer asi dado que muy probablemente habrán errores de cartografia en las sugerencias.
            Se deberia hacer un EditText en la app para escribir el nombre del tema Y ENTONCES insertar.

            IF @decision=1
                BEGIN
                    DECLARE @nombreTemaSugerido VARCHAR (30)

                    SET @nombreTemaSugerido=(SELECT nombreTema
                                             FROM Sugerencia
                                             WHERE ID=@IDSugerencia)

                    EXEC AnhadirTema @nombreTemaSugerido
                END
    */
    END
go

CREATE FUNCTION RegistroMensajesInicialesDelChat(@chatID INT) RETURNS TABLE AS
RETURN
SELECT TOP 50 Remitente,Cuerpo,FechaHoraEnvio
FROM Mensaje
WHERE ChatID=@chatID
ORDER BY FechaHoraEnvio DESC
go

CREATE   FUNCTION RegistroMensajesPreviosDelChat(@chatID INT, @dateTime DATETIME) RETURNS TABLE AS
RETURN
SELECT TOP 50 Remitente,Cuerpo,FechaHoraEnvio
FROM Mensaje
WHERE ChatID=@chatID AND FechaHoraEnvio<@dateTime
ORDER BY FechaHoraEnvio DESC
go

CREATE   FUNCTION SeleccionarUsuariosConTemasComunes (@uidSolicitante VARCHAR(128)) RETURNS TABLE
AS
    RETURN
        SELECT DISTINCT PResto.uid_usuario
        FROM Preferencia PResto
        INNER JOIN Preferencia PSolicitante ON PSolicitante.IDTema = PResto.IDTema
        WHERE PSolicitante.uid_usuario = @uidSolicitante
        AND PResto.uid_usuario != @uidSolicitante

        EXCEPT
        (
        SELECT uid_usuario_1 AS UIDUsuario
        FROM Chat
        WHERE uid_usuario_2=@uidSolicitante

        UNION
        
        SELECT uid_usuario_2 AS UIDUsuario
        FROM Chat
        WHERE uid_usuario_1=@uidSolicitante
        )
go

CREATE   FUNCTION TemasComunesEntreDosUsuarios (@uidSolicitante VARCHAR(128), @uidSolicitado VARCHAR(128)) RETURNS TABLE
AS
    RETURN
SELECT COUNT(ID) NumeroTemas
FROM Tema
INNER JOIN Preferencia PSolicitante on Tema.ID = PSolicitante.IDTema
INNER JOIN Preferencia PSolicitado ON Tema.ID = PSolicitado.IDTema AND PSolicitante.IDTema=PSolicitado.IDTema
WHERE PSolicitante.uid_usuario=@uidSolicitante AND PSolicitado.uid_usuario=@uidSolicitado
go

CREATE   FUNCTION TemasDeUsuario(@uidSolicitante VARCHAR(128)) RETURNS TABLE AS
    RETURN
SELECT ID, nombre
FROM Tema
INNER JOIN Preferencia P on Tema.ID = P.IDTema
WHERE P.uid_usuario=@uidSolicitante
go

CREATE   FUNCTION TodosLosTemas() RETURNS TABLE AS
    RETURN
SELECT ID, nombre
FROM Tema
go

CREATE   FUNCTION UltimoMensajeDelChat(@chatID INT) RETURNS TABLE AS
RETURN

SELECT TOP 1 Remitente,Cuerpo,FechaHoraEnvio
FROM Mensaje
WHERE ChatID=@chatID
ORDER BY FechaHoraEnvio DESC
go

