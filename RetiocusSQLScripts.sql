CREATE OR ALTER PROC AnhadirUsuario
@NumeroTelefono CHAR(9),
@NombreUsuario VARCHAR(50),
@Contrasenha VARCHAR(10),
@Foto VARBINARY(MAX)=NULL
AS
    BEGIN
        INSERT INTO Usuario (NumeroTelefono, nombre, contrasenha, fotoPerfil) VALUES
        (@NumeroTelefono, @NombreUsuario, ENCRYPTBYPASSPHRASE(@NumeroTelefono,@Contrasenha), @Foto)
    END

GO

CREATE OR ALTER FUNCTION TraerContrasenhaDeUsuario(
    @NumeroTelefono CHAR(9)
) RETURNS VARCHAR(MAX) AS
    BEGIN
        RETURN (
            SELECT DECRYPTBYPASSPHRASE(@NumeroTelefono,contrasenha)
            FROM Usuario
            WHERE NumeroTelefono=@NumeroTelefono
            )
    END

GO

CREATE OR ALTER PROC CambiarContrasenha
@NumeroTelefono CHAR(9),
@ContrasenhaAntigua VARCHAR(10),
@ContrasenhaNueva VARCHAR(10),
@SUCCESS BIT OUTPUT
AS
    BEGIN
        SET @SUCCESS=0

        DECLARE @ContrasenhaActual VARCHAR(10)

        EXEC @ContrasenhaActual=TraerContrasenhaDeUsuario @NumeroTelefono

        IF @ContrasenhaActual = @ContrasenhaAntigua
            BEGIN
                SET @SUCCESS=1

                UPDATE Usuario
                SET contrasenha=ENCRYPTBYPASSPHRASE(@NumeroTelefono,@ContrasenhaNueva)
                WHERE NumeroTelefono=@NumeroTelefono
            end
    END

CREATE OR ALTER FUNCTION ComprobarInicioSesion


