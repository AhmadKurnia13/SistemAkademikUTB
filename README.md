<p align="center">
  <img src="img/logo_utb.png" alt="Logo UTB" width="150" />
</p>

<h1 align="center">🎓 Sistem Informasi Akademik UTB</h1>
<p align="center">
  <strong>Aplikasi Desktop Java Swing Premium untuk Manajemen Data Akademik Terpadu</strong><br/>
  <em>Tugas Akhir / Ujian Tengah Semester (UTS) — Pemrograman Berorientasi Objek II</em>
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Java-JDK_17+-orange?style=for-the-badge&logo=openjdk" />
  <img src="https://img.shields.io/badge/IDE-NetBeans-blue?style=for-the-badge&logo=apache-netbeans-ide" />
  <img src="https://img.shields.io/badge/Database-MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white" />
  <img src="https://img.shields.io/badge/UI-Premium_Dark_Mode-000000?style=for-the-badge" />
</p>

---

## 📋 Deskripsi Proyek
**Sistem Informasi Akademik UTB** adalah sebuah aplikasi perangkat lunak berbasis *Java Desktop* yang dirancang untuk mempermudah pengelolaan data akademik di lingkungan Universitas Teknologi Bandung (UTB). Dibangun dengan arsitektur **JDesktopPane** dan **JInternalFrame**, aplikasi ini mengusung desain *Premium Dark Mode* yang elegan, profesional, dan bebas dari tampilan kaku khas Java Swing bawaan.

Aplikasi ini telah terintegrasi secara penuh dengan *Database Relasional MySQL*, mendukung operasi **CRUD (Create, Read, Update, Delete)** untuk berbagai entitas akademik mulai dari data *Master* hingga *Transaksi*.

---

## 🌟 Fitur Utama Aplikasi

Aplikasi ini dilengkapi dengan serangkaian fitur profesional yang dirancang khusus untuk memenuhi standar akademik:

- 🔐 **Secure Login System** — Autentikasi pengguna dengan validasi langsung dari database untuk mengamankan akses ke dalam Dashboard Utama.
- 🎨 **Premium Dark Mode UI** — Antarmuka pengguna (*User Interface*) modern, elegan, dan *eye-catching* tanpa menggunakan library eksternal (Pure Java Swing).
- 🗂️ **Master Data Management** — Modul lengkap untuk mengelola data inti (*CRUD*):
  - **Data User:** Manajemen akun pengguna (Tambah User & Ganti Password).
  - **Data Mahasiswa:** Pengolahan identitas mahasiswa aktif.
  - **Data Dosen:** Pengolahan data pengajar dan NIDN.
  - **Data Mata Kuliah:** Manajemen kurikulum dan SKS.
- 🔄 **Modul Transaksi Akademik** — 
  - **KRS (Kartu Rencana Studi):** Pendaftaran mata kuliah per semester untuk mahasiswa.
  - **Input Nilai:** Modul *Critical* yang dibatasi secara ketat untuk penginputan nilai huruf terstandarisasi (`A`, `B`, `C`, `D`, `E`).
- 📊 **Dynamic Data Rendering** — Menampilkan data *real-time* ke dalam tabel (`JTable`) yang telah dikustomisasi secara mulus agar menyatu dengan tema gelap (*borderless & flat*).

---

## 🚀 Cara Menjalankan Aplikasi

Ikuti langkah-langkah di bawah ini untuk menjalankan aplikasi di lingkungan lokal Anda:

### 1. Persiapan Database (XAMPP)
1. Buka dan jalankan **XAMPP Control Panel**.
2. Klik tombol **Start** pada modul **Apache** dan **MySQL**.
3. Buka *browser* dan akses [http://localhost/phpmyadmin](http://localhost/phpmyadmin).
4. Buat *database* baru dengan nama: `db_uts_pbo_ahmad`.
5. *Import* file SQL (jika tersedia) untuk membentuk struktur tabel secara otomatis.

### 2. Konfigurasi IDE (NetBeans)
1. Buka **Apache NetBeans IDE**.
2. Pilih menu **File > Open Project**, lalu cari dan buka folder proyek `SistemAkademikUTB`.
3. Pastikan **MySQL Connector/J** (`mysql-connector-j-9.7.0.jar`) sudah ditambahkan ke dalam daftar *Libraries* proyek Anda.
4. Lakukan *Clean and Build* proyek untuk memastikan tidak ada *error*.

### 3. Eksekusi Program
1. Di panel *Projects* sebelah kiri, cari file kelas utama penggerak aplikasi: `src/Akademik/SistemAkademikUTB.java`.
2. Klik kanan pada file tersebut dan pilih **Run File** (atau tekan `Shift + F6`).
3. Anda akan disambut oleh layar **Form Login**. Masukkan *username* dan *password* Anda untuk masuk ke dalam **Dashboard (Main Menu)**.

---

## 📸 Dokumentasi & Screenshot

Semua dokumentasi visual terkait antarmuka (*UI*) dan demonstrasi fitur (Form Login, Dashboard, Form Input Nilai, dll) disimpan secara rapi di dalam folder berikut:
👉 **`/dokumentasi/`** *(Buka folder ini untuk melihat screenshot UI aplikasi)*

---

## 🎥 Video Demonstrasi

> Penjelasan detail mengenai kode, alur berjalannya aplikasi, dan demonstrasi interaksi dengan *Database Relasional* dapat ditonton melalui tautan berikut:

👉 **[Link Video YouTube Menyusul]**

---

## 👤 Identitas Pengembang (Author)

| Informasi Akademik | Detail |
| :--- | :--- |
| **Nama Mahasiswa** | **Ahmad Kurnia** |
| **Nomor Induk Mahasiswa (NIM)** | `24552011297` |
| **Kelas** | TIF RP 24 CNS D |
| **Program Studi** | Teknik Informatika |
| **Mata Kuliah** | Pemrograman Berorientasi Objek II |
| **Institusi** | Universitas Teknologi Bandung (UTB) |
| **Dosen Pengampu** | Bapak Iwan Ridwan, S.T., M.Kom. |

<p align="center">
  <em>Dibuat dengan ❤️ untuk memenuhi tugas Ujian Tengah Semester.</em>
</p>
