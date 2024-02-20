# Users

An application service with responsibilities in centralized user management,
user authentication and Oauth2 application-wide authorization.
Based on Spring Security and Spring Authorization Server.

## Usage scenario

Unlike classic Oauth2 usage scenario for authorization multiple third-party clients
to access to resources, service configured for one public client only - the application
single-page (SPA) frontend application, that operates on the same domain name
as all the other application services. Third-party access is not supposed.

To authenticate and authorize end-users to access to resources, SPA does the following steps:

1. Authenticate an end-user by sending a `POST` request to the `/login` endpoint
as a typical Spring Security form-based authentication.
2. SPA receives a session ID and stores it in an HTTP cookie.
3. While HTTP session is valid, SPA can get access tokens on the dedicated endpoints
and with the steps, according to the specification of the Authorization Code
with Proof Key for Code Exchange (PKCE) Authorization Grant.
After form-based authentication, any user participation is not required.

## Asymmetric cryptography keys

> [!CAUTION]
> Be careful with the private key and do not provide access to it to another person.
> Otherwise all the generated tokens will be compromised.

To use the service in the proper way, it is required to specify digital signature certificates
to tokens signing and verification. While certificates are not specified, they will be generated
each time the service starts.
At the moment, RSA SHA-256 scheme supported and tested only.
To generate and specify certificates, please do the following steps:

1. Generate a new key pair, for example, with OpenSSL

```shell
# Generate a new RSA key pair
openssl genrsa -out keypair.pem 2048
```

```shell
# Extract the public key
openssl rsa -in keypair.pem -pubout -out public.pem
```

```shell
# Create the private key in PKCS#8 format
openssl pkcs8 -topk8 -inform PEM -outform PEM -nocrypt -in keypair.pem -out private.pem
```

2. Put the files to the `users/src/main/resources/security` folder or folder with service `JAR` file
3. Specify the paths to the files in the properties file, for example:

```yaml
app.security.key:
    public: "classpath:security/public.pem"
    private: "classpath:security/private.pem"
```

4. Restart the service

> [!NOTE]
> Both of the keys or none of them should be specified. One key is not allowed.
