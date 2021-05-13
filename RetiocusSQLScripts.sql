CREATE OR ALTER PROCEDURE AnhadirChat
@NumeroTelefonoSolicitante CHAR(9),
@NumeroTelefonoSolicitado CHAR(9)
AS
    BEGIN
        IF @NumeroTelefonoSolicitado>@NumeroTelefonoSolicitante

            INSERT INTO Chat (FechaCreacion, NumeroUsuarioUno, NumeroUsuarioDos)
            VALUES (GETDATE(),@NumeroTelefonoSolicitante,@NumeroTelefonoSolicitado)

        ELSE IF @NumeroTelefonoSolicitado<@NumeroTelefonoSolicitante

            INSERT INTO Chat (FechaCreacion, NumeroUsuarioUno, NumeroUsuarioDos)
            VALUES (GETDATE(),@NumeroTelefonoSolicitado,@NumeroTelefonoSolicitante)

        ELSE

            THROW 51002,'No se puede hacer un chat con la misma persona.',1
    END
go

CREATE OR ALTER PROCEDURE AnhadirSugerencia
@NumeroSugestor CHAR(9),
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

                    INSERT INTO Sugerencia (ID, NumeroSugestor, nombreTema, motivos, aceptada)

                    VALUES (1,@NumeroSugestor,@NombreTema,@motivos,NULL)

                    SET IDENTITY_INSERT Sugerencia OFF
                END

                ELSE

                    INSERT INTO Sugerencia (NumeroSugestor, nombreTema, motivos, aceptada)

                    VALUES (@NumeroSugestor,@NombreTema,@motivos,NULL)
            END
        ELSE
        THROW 51004,'El tema sugerido ya esta en la base de datos.',1;
    END
go

CREATE OR ALTER PROC AnhadirUsuario
@NumeroTelefono CHAR(9),
@NombreUsuario VARCHAR(50),
@Contrasenha VARCHAR,
@Foto VARBINARY(MAX)=NULL
AS
    BEGIN
        INSERT INTO Usuario (NumeroTelefono, nombre, contrasenha, fotoPerfil) VALUES
        (@NumeroTelefono, @NombreUsuario, ENCRYPTBYPASSPHRASE(@NumeroTelefono,@Contrasenha), @Foto)
    END
go

CREATE OR ALTER FUNCTION TraerContrasenhaDeUsuario(
    @NumeroTelefono CHAR(9)
) RETURNS VARCHAR AS
    BEGIN
        RETURN (
            SELECT CAST(DECRYPTBYPASSPHRASE(NumeroTelefono,contrasenha) AS VARCHAR)
            FROM Usuario
            WHERE NumeroTelefono=@NumeroTelefono
            )
    END
go

CREATE OR ALTER PROC CambiarContrasenha
@NumeroTelefono CHAR(9),
@ContrasenhaAntigua VARCHAR,
@ContrasenhaNueva VARCHAR,
@ContrasenhaAntiguaAcertada BIT OUTPUT
AS
    BEGIN
        SET @ContrasenhaAntiguaAcertada=0

        DECLARE @ContrasenhaActual VARCHAR

        EXEC @ContrasenhaActual=TraerContrasenhaDeUsuario @NumeroTelefono

        IF @ContrasenhaActual = @ContrasenhaAntigua
            BEGIN
                SET @ContrasenhaAntiguaAcertada=1

                UPDATE Usuario
                SET contrasenha=ENCRYPTBYPASSPHRASE(@NumeroTelefono,@ContrasenhaNueva)
                WHERE NumeroTelefono=@NumeroTelefono
            END
        ELSE
            THROW 51001,'Contrasenha antigua incorrecta.',1;
    END
go

CREATE OR ALTER FUNCTION ComprobarInicioSesion(
    @Usuario VARCHAR(50),
    @ContrasenhaIntroducida VARCHAR
) RETURNS BIT AS
    BEGIN
        DECLARE @inicioSesionValido BIT

        SET @inicioSesionValido=0

        DECLARE @ContrasenhaAntigua VARCHAR

        --Añadir consulta para sacar num telefono

        EXEC @ContrasenhaAntigua= TraerContrasenhaDeUsuario @Usuario

        IF @ContrasenhaAntigua=@ContrasenhaIntroducida

            SET @inicioSesionValido=1

        RETURN @inicioSesionValido
    END
go

CREATE OR ALTER PROCEDURE EditarPerfil
@NumeroTelefono CHAR(9),
@NombreUsuarioNuevo VARCHAR(50) = NULL,
@ContrasenhaAntigua VARCHAR = NULL,
@ContrasenhaNueva VARCHAR = NULL,
@FotoNueva VARBINARY(8000) = NULL,
@ExisteUsuario BIT = 0 OUTPUT
AS
    BEGIN
        IF @NombreUsuarioNuevo IS NOT NULL

            BEGIN

                IF EXISTS(SELECT nombre
                          FROM Usuario
                          WHERE nombre=@NombreUsuarioNuevo)

                    SET @ExisteUsuario=1

                ELSE

                    UPDATE Usuario
                    SET nombre = @NombreUsuarioNuevo
                    WHERE NumeroTelefono=@NumeroTelefono

            END
        IF @ContrasenhaAntigua IS NOT NULL AND @ContrasenhaNueva IS NOT NULL

            BEGIN
                DECLARE @ContrasenhaAntiguaAcertada BIT

                EXEC CambiarContrasenha @NumeroTelefono, @ContrasenhaAntigua, @ContrasenhaNueva, @ContrasenhaAntiguaAcertada OUTPUT

                IF @ContrasenhaAntiguaAcertada=0

                    THROW 51003,'Contrasenha antigua incorrecta.',1;
            END

        IF @FotoNueva IS NOT NULL

            UPDATE Usuario
            SET fotoPerfil=@FotoNueva
            WHERE NumeroTelefono=@NumeroTelefono

    END
go

CREATE OR ALTER PROCEDURE EliminarChat
@NumeroTelefonoSolicitante CHAR(9),
@NumeroTelefonoSolicitado CHAR(9)
AS
    BEGIN
        IF @NumeroTelefonoSolicitado>@NumeroTelefonoSolicitante

            DELETE FROM Chat
            WHERE NumeroUsuarioUno=@NumeroTelefonoSolicitante AND NumeroUsuarioDos=@NumeroTelefonoSolicitado

        ELSE IF @NumeroTelefonoSolicitado<@NumeroTelefonoSolicitante

            DELETE FROM Chat
            WHERE NumeroUsuarioUno=@NumeroTelefonoSolicitado AND NumeroUsuarioDos=@NumeroTelefonoSolicitante

    END
go

CREATE OR ALTER PROCEDURE AnhadirTema
@nombre VARCHAR(30)
AS
    BEGIN
        IF NOT EXISTS(SELECT * FROM Tema)
            DBCC CHECKIDENT ('Tema', RESEED, 1);

        INSERT INTO Tema (nombre)
        VALUES (@nombre)
    END
go

CREATE OR ALTER PROCEDURE EvaluarSugerencia
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

CREATE OR ALTER PROCEDURE AsignarAdministrador
@NumeroTelefono CHAR(9)
AS
    BEGIN
        UPDATE Usuario
        SET esAdmin=1
        WHERE NumeroTelefono=@NumeroTelefono
    END
go
CREATE OR ALTER FUNCTION TemasComunesEntreDosUsuarios (@numeroSolicitante CHAR(9), @numeroSolicitado CHAR(9)) RETURNS TABLE
AS
    RETURN
SELECT nombre
FROM Tema
INNER JOIN Preferencia PSolicitante on Tema.ID = PSolicitante.IDTema
INNER JOIN Preferencia PSolicitado ON Tema.ID = PSolicitado.IDTema AND PSolicitante.IDTema=PSolicitado.IDTema
WHERE PSolicitante.NumeroUsuario=@numeroSolicitante AND PSolicitado.NumeroUsuario=@numeroSolicitado

GO

CREATE OR ALTER FUNCTION SeleccionarUsuariosConTemasComunesYSuNumero (@numeroSolicitante CHAR(9)) RETURNS TABLE
AS
    RETURN
SELECT U.nombre, U.fotoPerfil, NumeroTemasComunesPorUsuario.NumeroTemasComunes
FROM Usuario U
INNER JOIN (
            SELECT PSolicitante.NumeroUsuario, COUNT(PSolicitante.IDTema) AS NumeroTemasComunes
            FROM Preferencia PSolicitante
            INNER JOIN Preferencia PResto ON PSolicitante.IDTema=PResto.IDTema
            WHERE PResto.NumeroUsuario=@numeroSolicitante AND PSolicitante.NumeroUsuario!=@numeroSolicitante
            GROUP BY PSolicitante.NumeroUsuario
            ) NumeroTemasComunesPorUsuario ON NumeroTemasComunesPorUsuario.NumeroUsuario=U.NumeroTelefono

GO