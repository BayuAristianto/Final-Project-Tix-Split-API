# 🚀 TixSplit API Automation Testing (`FinalProjectAPI`)

Proyek ini adalah kerangka kerja (*framework*) pengujian otomatis berbasis API (*API Test Automation*) untuk aplikasi **TixSplit** menggunakan **Java**, **Rest Assured**, **TestNG**, dan **Gradle**.

---

## 🛠️ Teknologi & Pustaka Utama
* **Java 21**: Bahasa pemrograman utama.
* **Gradle**: *Build tool* dan manajemen dependensi.
* **Rest Assured (`5.3.1`)**: Pustaka pengujian HTTP API (`GET`, `POST`, `PATCH`).
* **TestNG (`7.10.2`)**: *Test runner*, manajemen grup tes, dan *assertions*.
* **Hamcrest (`2.2`)**: Pustaka *matcher* untuk mempermudah validasi asersi response.
* **Org.JSON (`20230227`)**: Pembuatan *request payload* JSON dinamis (`JSONObject` & `JSONArray`).
* **Jackson Databind (`2.18.0`)**: Serialisasi dan deserialisasi data objek Java ke JSON.
* **Apache POI (`5.2.3`)**: Pembacaan dan pengelolaan data pengujian berbasis Excel (`.xls` / `.xlsx`).
* **Apache Commons Lang3 (`3.12.0`)**: Fungsi utilitas manipulasi string dan pembuatan data acak (*random generator*).
* **Extent Reports (`5.1.2`)**: Pembuatan laporan hasil pengujian visual berbasis HTML secara otomatis.
* **GitHub Actions**: Integrasi CI/CD otomatis menggunakan workflow `gradle-tests.yml`.


---

## 📂 Struktur Proyek

```text
FinalProjectAPI/
├── .github/
│   └── workflows/
│       └── gradle-tests.yml                # Konfigurasi CI/CD otomatis di GitHub Actions
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── body/
│   │   │   │   ├── auth/
│   │   │   │   │   └── LoginBody.java              # Payload builder untuk autentikasi login
│   │   │   │   ├── payloadEvent/
│   │   │   │   │   ├── CreateEventBody.java        # Payload builder untuk pembuatan event
│   │   │   │   │   └── UpdateEventBody.java        # Payload builder untuk pembaruan event
│   │   │   │   └── payloadSplitBill/
│   │   │   │       ├── CreateSplitBillBody.java    # Payload builder untuk pembuatan split bill
│   │   │   │       └── UpdateSplitBillBody.java    # Payload builder untuk pembaruan split bill & lineItems
│   │   │   └── utils/
│   │   │       ├── ConfigReader.java               # Pembaca konfigurasi dari config.properties
│   │   │       └── Utils.java                      # Fungsi pembantu (generator judul & tanggal dinamis)
│   │   └── resources/
│   │       ├── json/
│   │       │   ├── token.json                      # Menyimpan Bearer Token (Owner/Admin)
│   │       │   └── tokenMember.json                # Menyimpan Bearer Token (Member)
│   │       └── config.properties                   # Menyimpan baseUrl dan data konfigurasi lingkungan
│   └── test/
│       ├── java/
│       │   ├── base/
│       │   │   ├── ExtentReportListener.java       # Listener pembuatan laporan HTML otomatis dengan timestamp
│       │   │   └── UtilsTest.java                  # Fungsi ekstraksi pesan error response (JsonPath)
│       │   ├── runner/
│       │   │   ├── basicLogin.xml                  # Konfigurasi suite XML khusus modul autentikasi
│       │   │   └── testing.xml                     # Konfigurasi suite XML utama (seluruh skenario pengujian)
│       │   └── tests/
│       │       ├── auth/
│       │       │   └── BasicLoginTest.java         # Skenario pengujian login & pengambilan token
│       │       ├── eventTest/
│       │       │   ├── CreateEventTest.java        # Skenario pengujian pembuatan event (Positif & Negatif)
│       │       │   ├── GetEventTest.java           # Skenario pengujian pengambilan data event & validasi ID
│       │       │   └── UpdateEventTest.java        # Skenario pengujian pembaruan data event (PATCH)
│       │       └── splitBillTest/
│       │           ├── CreateSplitBillTest.java    # Skenario pengujian pembuatan tagihan split bill
│       │           ├── GetSplitBillTest.java       # Skenario pengujian pengambilan detail split bill
│       │           └── UpdateSplitBillTest.java    # Skenario pengujian pembaruan expense & validasi lineItems
│       └── resources/                              # Direktori resource tambahan untuk pengujian
├── test-output/                                    # Hasil laporan HTML Extent Reports (diabaikan oleh Git)
├── .gitignore                                      # Daftar file/folder yang diabaikan Git (termasuk test-output/)
├── build.gradle                                    # Daftar dependensi dan konfigurasi proyek Gradle
└── settings.gradle                                 # Pengaturan nama root project Gradle
```

---

## 🔄 Alur Pengujian (*End-to-End Testing Flow*)

1. **Inisialisasi & Konfigurasi (`@BeforeClass`)**:
    * Membaca `baseUrl` dari `src/main/resources/config.properties` melalui `ConfigReader`.
    * Mengeksekusi login atau memuat *Bearer Token* (`token.json` & `tokenMember.json`) untuk autentikasi *request*.
2. **Modul Autentikasi (`tests.auth`)**:
    * **BasicLoginTest**: Memvalidasi proses *login* pengguna (`Owner` & `Member`) dan mengekstrak *token* akses menggunakan `JsonPath`.
3. **Modul Event (`tests.eventTest`)**:
    * **CreateEventTest**: Membuat acara baru dengan judul dan tanggal dinamis dari `Utils`, serta menguji validasi *field* wajib pada *negative case*.
    * **GetEventTest**: Mengambil detail acara serta memvalidasi penanganan ID yang tidak ditemukan (*Invalid ID*).
    * **UpdateEventTest**: Memperbarui sebagian data acara menggunakan metode `PATCH`.
4. **Modul Split Bill (`tests.splitBillTest`)**:
    * **CreateSplitBillTest**: Membuat tagihan patungan beserta daftar peserta (`participants`).
    * **GetSplitBillTest**: Mengambil detail informasi tagihan patungan yang telah dibuat.
    * **UpdateSplitBillTest**: Memperbarui pengeluaran (`expenses`) beserta rincian item (`lineItems`), termasuk skenario validasi jumlah rincian melebihi total (*Detail Exceeds Total - 400 Bad Request*).
5. **Pelaporan & CI/CD Otomatis**:
    * `ExtentReportListener` mencatat seluruh status eksekusi (*Pass/Fail/Skip*) ke dalam file laporan HTML unik berstempel waktu (`ExtentReport_yyyyMMdd_HHmmss.html`) di folder `test-output/`.
    * Setiap *push* ke repositori otomatis memicu alur kerja pengujian pada `.github/workflows/gradle-tests.yml`.

---

## ▶️ Cara Menjalankan Pengujian

### 1. Prasyarat
* Terinstal **JDK 21**.
* Koneksi internet aktif untuk mengakses server API target (`https://dibimbing.id`).

### 2. Menjalankan via IntelliJ IDEA (TestNG Runner)
1. Buka folder `src/test/java/runner/`.
2. Klik kanan pada file **`testing.xml`**.
3. Pilih **Run '...testing.xml'**.

### 3. Menjalankan via Terminal (Gradle)
Buka terminal di direktori utama proyek, lalu jalankan perintah berikut:
```bash
./gradlew clean test
```

---

## 📊 Melihat Hasil Laporan (*Test Report*)
Setelah eksekusi selesai, buka folder **`test-output/`**, klik kanan pada file **`ExtentReport_<timestamp>.html`** terbaru, lalu pilih **Open In → Browser → Chrome**.
