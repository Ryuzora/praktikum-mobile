# Modul 5 Notes

## Caching strategy

Aplikasi ini memakai strategi cache-first dengan refresh dari network:

1. Repository membaca data film dari Room terlebih dahulu.
2. Jika cache Room tersedia, data langsung dikirim ke UI melalui Flow agar aplikasi tetap bisa dipakai saat offline.
3. Setelah itu repository mencoba mengambil data terbaru dari TMDB API memakai Retrofit.
4. Jika request TMDB berhasil, data lama di Room diganti dengan data terbaru.
5. Jika request gagal dan cache sudah ada, UI tetap memakai cache. Error hanya ditampilkan jika tidak ada cache sama sekali.

Strategi ini dipilih karena data popular movies tidak harus real-time per detik. Pengguna mendapat tampilan cepat dari database lokal, tetapi data tetap disegarkan saat koneksi tersedia.

## Persistence

Room menyimpan daftar film sebagai data relasional lokal. SharedPreferences menyimpan data ringan berupa film terakhir yang dibuka.

## API key

Tambahkan API key TMDB ke `local.properties`:

```properties
TMDB_API_KEY=isi_api_key_kamu
```
