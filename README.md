# Cara Menjalankan
## Melalui Lokal
1. Pastikan sudah install Java minimal versi 11
2. Jalankan perintah `cd webservice`
3. Jalankan perintah `mvn clean compile assembly:single` untuk membuat file .jar
4. Jalankan file .jar dengan perintah `java -jar target/webservice-1.0-SNAPSHOT-jar-with-dependencies.jar`

## Melalui Docker
1. Pastikan sudah install Docker dan Docker Compose
2. Jalankan `docker-compose up` setelah server juga sudah dijalankan dengan docker

# Deskripsi Singkat Web Service
Web service dibuat dengan Java menggunakan Maven dan JAX-WS. Web service dibuat dengan pendekatan bottom-up. Web service yang disediakan adalah antarmuka untuk toko agar dapat melakukan penambahan request ke basis data pabrik, rate-limiter penambahan request, pengecekkan status request, dan untuk pencarian daftar resep yang disediakan oleh pabrik. 

# Deskripsi Singkat Aplikasi
Server dibuat menggunakan Node.js dan framework Express.js. Basis data yang digunakan adalah MySQL untuk menyimpan data resep, request, dan bahan baku serta Redis untuk caching data. Aplikasi juga dapat mengirimkan email ke admin setiap kali menerima request baru menggunakan library nodemailer. Aplikasi dapat melakukan autentikasi pengguna menggunakan JWT. Server akan melakukan autentikasi request dari client melalui HTTP-only cookie yang dimiliki client yang seharusnya memiliki token JWT. Server akan berkomunikasi dengan toko melalui web service.

# Skema Basis Data
Skema basis data yang digunakan adalah skema basis data relasional melalui DBMS MySQL. Terdapat tabel request, log_request, recipe, material, dan material_recipe. Tabel request adalah untuk menyimpan request dari toko yang ada. Tabel log_request adalah untuk rate limiter request dari toko. Tabel recipe adalah untuk menyimpan informasi resep dorayaki yang berisi nama dan deskripsi resep. Tabel material adalah untuk menyimpan informasi bahan baku resep yang berisi nama dan stok bahan baku. Tabel material_recipe adalah untuk menyimpan informasi bahan baku apa saja yang ada di tiap resep.

# Pembagian Tugas
REST

- Basis data: 13519096
- Login + Register: 13519096
- Autentikasi pengguna dengan JWT: 13519096
- Detail resep: 13519096
- Notifikasi Email: 13519096
- Caching query/response: 13519096
- Docker: 13519096
- Penampilan dan Pembuatan Resep: 13519090
- Penampilan dan Pembuatan Bahan Baku: 13519090
- Handle Request : 13519048


SOAP

- Desain interface dan class untuk service: 13519096
- Penambahan request: 13519096
- Get status request: 13519090
- Get all recipe: 13519048
- Docker: 13519096



