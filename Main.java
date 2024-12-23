import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Main {
    private static Connection koneksi;  // Variabel statik untuk menyimpan koneksi database

    private static final String PASSWORD = "admin123"; // Password yang harus dimasukkan

   // Method untuk membuat koneksi ke database
    @SuppressWarnings("CallToPrintStackTrace")
   public static void koneksiDatabase() {
    try { //Exception Handling
        // Memuat driver JDBC untuk MySQL
        Class.forName("com.mysql.cj.jdbc.Driver"); // Constructor: Memuat driver untuk koneksi
        // String: URL koneksi ke database
        String url = "jdbc:mysql://localhost:3306/hotel_db?useSSL=false&serverTimezone=UTC";
        String user = "root"; // String: Username database
        String password = ""; // String: Password database

        // Membuka koneksi ke database
        koneksi = DriverManager.getConnection(url, user, password); // Constructor: Membuat koneksi menggunakan DriverManager
        System.out.println("Koneksi ke database berhasil."); // String: Pesan sukses
    } catch (ClassNotFoundException | SQLException e) { // Exception Handling: Menangkap kesalahan
        System.out.println("Koneksi ke database gagal."); // String: Pesan error
        e.printStackTrace(); // Menampilkan error stack trace untuk debugging
        System.exit(0); // Percabangan: Keluar dari program jika koneksi gagal
    }
}

    @SuppressWarnings("CallToPrintStackTrace")
    public static void tutupKoneksi() {
    try {  //Exception Handling
        // Percabangan: Memeriksa apakah koneksi tidak null dan belum ditutup
        if (koneksi != null && !koneksi.isClosed()) {
            koneksi.close(); // Menutup koneksi ke database
            System.out.println("Koneksi database ditutup."); // String: Pesan sukses
        }
    } catch (SQLException e) { // Exception Handling: Menangkap kesalahan saat menutup koneksi
        System.out.println("Gagal menutup koneksi."); // String: Pesan error
        e.printStackTrace(); // Menampilkan error stack trace untuk debugging
    }
}

    // Verifikasi password
    public static void verifikasiPassword(Scanner scanner) {
        // Meminta pengguna memasukkan password
        System.out.print("Masukkan password untuk mengakses sistem: "); // String: Menampilkan pesan ke pengguna
        String inputPassword = scanner.nextLine(); // String: Membaca input password dari pengguna
    
        // Percabangan: Memeriksa apakah password yang dimasukkan cocok dengan password yang disimpan
        if (!inputPassword.equals(PASSWORD)) {
            System.out.println("Password salah. Program akan keluar."); // Pesan jika password salah
            System.exit(0); // Menghentikan program jika password salah
        }
    
        // Jika password benar
        System.out.println("Password benar "); // Pesan jika password benar
        System.out.println("Selamat datang di sistem manajemen hotel "); // String: Menampilkan pesan selamat datang
    }
    

    // Fungsi CAPTCHA
    public static void captcha(Scanner scanner) {
        // Membuat objek Random untuk menghasilkan angka acak
        Random random = new Random(); // Constructor: Membuat instance Random
        int angka1 = random.nextInt(50) + 1; // Perhitungan Matematika: Menghasilkan angka acak 1-50
        int angka2 = random.nextInt(50) + 1; // Perhitungan Matematika: Menghasilkan angka acak 1-50
        int hasil = angka1 + angka2; // Perhitungan Matematika: Menjumlahkan angka1 dan angka2
    
        // Menampilkan soal CAPTCHA kepada pengguna
        System.out.println("=== Verifikasi CAPTCHA ===");
        System.out.println("Berapa hasil dari: " + angka1 + " + " + angka2 + "?"); // String: Digunakan untuk menyusun teks soal CAPTCHA
        System.out.print("Jawaban Anda: ");
    
        try {
            // Membaca jawaban pengguna
            int jawaban = scanner.nextInt(); // Exception Handling: Potensi InputMismatchException jika input tidak berupa angka
            scanner.nextLine(); // Membersihkan buffer input
    
            // Percabangan: Memeriksa apakah jawaban pengguna benar atau salah
            if (jawaban != hasil) {
                System.out.println("CAPTCHA salah. Program akan keluar."); // Pesan jika jawaban salah
                System.exit(0); // Menghentikan program jika CAPTCHA salah
            }
    
            // Jika jawaban benar
            System.out.println("CAPTCHA benar "); // Pesan jika CAPTCHA benar
            System.out.println("Selamat datang! ");
        } catch (InputMismatchException e) { // Exception Handling: Menangkap kesalahan input yang tidak sesuai
            System.out.println("Input tidak valid. Program akan keluar."); // Pesan jika input tidak valid
            System.exit(0); // Menghentikan program
        }
    }
    

    // Fungsi Create: Menambah data kamar ke database
    @SuppressWarnings("CallToPrintStackTrace")
    public static void tambahKamar(int nomorKamar, String tipeKamar, double hargaPerMalam) {
        // Query SQL untuk memeriksa apakah nomor kamar sudah ada
        String checkSql = "SELECT 1 FROM kamar WHERE nomor_kamar = ?";
    
        try (PreparedStatement checkStmt = koneksi.prepareStatement(checkSql)) { 
            // Exception handling: Membuka statement SQL untuk pengecekan
            checkStmt.setInt(1, nomorKamar); // Mengatur parameter query untuk nomor kamar
            ResultSet rs = checkStmt.executeQuery(); // Menjalankan query dan menyimpan hasilnya
    
            // Percabangan: Memeriksa apakah nomor kamar sudah ada di database
            if (rs.next()) {
                System.out.println("Nomor kamar sudah ada, tidak ditambahkan."); // Pesan jika nomor kamar sudah ada
                return; // Keluar dari metode tanpa menambahkan data baru
            }
    
            // Query SQL untuk menambahkan data kamar baru ke tabel
            String sql = "INSERT INTO kamar (nomor_kamar, tipe_kamar, harga_per_malam, tersedia) VALUES (?, ?, ?, ?)";
            try (PreparedStatement stmt = koneksi.prepareStatement(sql)) {
                // Mengatur parameter untuk query INSERT
                stmt.setInt(1, nomorKamar); // Parameter nomor kamar
                stmt.setString(2, tipeKamar); // Parameter tipe kamar
                stmt.setDouble(3, hargaPerMalam); // Parameter harga per malam
                stmt.setBoolean(4, true); // Parameter tersedia (default: true)
    
                stmt.executeUpdate(); // Menjalankan query untuk menambahkan data
                System.out.println("Data kamar berhasil ditambahkan."); // Pesan sukses
            } catch (SQLException e) { // Exception handling untuk query INSERT
                System.out.println("Gagal menambahkan data kamar."); // Pesan error
                e.printStackTrace(); // Menampilkan rincian error untuk debugging
            }
        } catch (SQLException e) { // Exception handling untuk pengecekan data
            System.out.println("Gagal memeriksa data kamar."); // Pesan error
            e.printStackTrace(); // Menampilkan rincian error untuk debugging
        }
    }
    

    // Fungsi Read: Membaca data kamar dari database
    @SuppressWarnings("CallToPrintStackTrace")
    public static void bacaKamar() {
        // Query SQL untuk membaca semua data dari tabel 'kamar'
        String sql = "SELECT nomor_kamar, tipe_kamar, harga_per_malam, tersedia FROM kamar";
    
        try (Statement stmt = koneksi.createStatement(); ResultSet rs = stmt.executeQuery(sql)) { 
            // Exception handling: Membuka statement dan eksekusi query
            // Perulangan: Mengiterasi hasil query menggunakan while loop
            while (rs.next()) {
                // Menampilkan data kamar satu per satu
                System.out.println("Nomor Kamar: " + rs.getInt("nomor_kamar")); // Menampilkan nomor kamar
                System.out.println("Tipe Kamar: " + rs.getString("tipe_kamar")); // Menampilkan tipe kamar
                System.out.println("Harga per Malam: Rp " + rs.getDouble("harga_per_malam")); // Menampilkan harga per malam
                
                // Percabangan: Menentukan apakah kamar tersedia atau tidak
                System.out.println("Tersedia: " + (rs.getBoolean("tersedia") ? "Ya" : "Tidak")); 
                System.out.println("----------------------------------");
            }
        } catch (SQLException e) { // Exception handling: Menangkap kesalahan saat eksekusi query SQL
            System.out.println("Gagal membaca data kamar."); // Menampilkan pesan error
            e.printStackTrace(); // Menampilkan rincian error untuk debugging
        }
    }
    

    // Fungsi Update: Memperbarui data kamar di database
    @SuppressWarnings("CallToPrintStackTrace")
    public static void updateKamar(Scanner scanner) {
        // Menampilkan daftar kamar yang tersedia
        bacaKamar();
    
        // Meminta pengguna memasukkan nomor kamar yang akan diperbarui
        System.out.print("Masukkan nomor kamar yang ingin diperbarui: ");
        int nomorKamar = scanner.nextInt();
        scanner.nextLine(); // Membersihkan buffer input
    
        // Percabangan: Memeriksa apakah nomor kamar valid
        if (!cekNomorKamar(nomorKamar)) {
            System.out.println("Nomor kamar tidak ditemukan."); // Pesan jika nomor kamar tidak ditemukan
            return; // Keluar dari metode jika nomor kamar tidak valid
        }
    
        // Meminta data baru untuk memperbarui kamar
        System.out.print("Masukkan tipe kamar baru: ");
        String tipeBaru = scanner.nextLine(); // Membaca tipe kamar baru
        System.out.print("Masukkan harga baru per malam: ");
        double hargaBaru = scanner.nextDouble(); // Membaca harga kamar baru
        scanner.nextLine(); // Membersihkan buffer input
    
        // Query SQL untuk memperbarui data kamar
        String sql = "UPDATE kamar SET tipe_kamar = ?, harga_per_malam = ? WHERE nomor_kamar = ?";
        
        try (PreparedStatement stmt = koneksi.prepareStatement(sql)) { // Exception handling: Membuka koneksi SQL
            // Mengatur parameter query SQL
            stmt.setString(1, tipeBaru); // Parameter tipe kamar
            stmt.setDouble(2, hargaBaru); // Parameter harga kamar
            stmt.setInt(3, nomorKamar); // Parameter nomor kamar
    
            // Menjalankan query update
            stmt.executeUpdate();
            System.out.println("Data kamar berhasil diperbarui."); // Pesan sukses
        } catch (SQLException e) { // Exception handling: Menangkap kesalahan SQL
            System.out.println("Gagal memperbarui data kamar."); // Pesan jika terjadi error
            e.printStackTrace(); // Menampilkan detail error untuk debugging
        }
    }
    
    // Fungsi Delete: Menghapus data kamar dari database
    @SuppressWarnings("CallToPrintStackTrace")
    public static void hapusKamar(int nomorHapus) {
        String sql = "DELETE FROM kamar WHERE nomor_kamar = ?";
        //Menggunakan try-with-resources untuk memastikan statement ditutup secara otomatis setelah selesai.
        try (PreparedStatement stmt = koneksi.prepareStatement(sql)) {
            stmt.setInt(1, nomorHapus);
            stmt.executeUpdate();
            System.out.println("Data kamar dengan nomor " + nomorHapus + " berhasil dihapus.");
        } catch (SQLException e) { // Menangkap exception jika ada kesalahan dalam eksekusi SQL
            System.out.println("Gagal menghapus data kamar.");
            e.printStackTrace();//Menampilkan rincian kesalahan (stack trace) untuk membantu debugging jika terjadi masalah saat eksekusi query
        }
    }
    
    // Fungsi untuk mengecek apakah nomor kamar ada di database
    @SuppressWarnings("CallToPrintStackTrace")
    public static boolean cekNomorKamar(int nomorKamar) {
        String sql = "SELECT 1 FROM kamar WHERE nomor_kamar = ?";
        //try-catch digunakan untuk menangani pengecualian yang mungkin terjadi selama eksekusi query SQL
        try (PreparedStatement stmt = koneksi.prepareStatement(sql)) {
            stmt.setInt(1, nomorKamar);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // Mengembalikan true jika nomor kamar ditemukan
            }
        } catch (SQLException e) { //Exception Handling
            System.out.println("Gagal mengecek nomor kamar.");
            e.printStackTrace();
        }
        return false; // Mengembalikan false jika terjadi kesalahan
    }

    // Fungsi Tanggal dengan validasi nomor kamar
    public static void tanggal(Scanner scanner) {
        try {
            System.out.print("Masukkan nomor kamar: ");
            int nomorKamar = scanner.nextInt();
            scanner.nextLine(); // Bersihkan buffer input
            
            if (!cekNomorKamar(nomorKamar)) { //Percabangan (if statement)
                System.out.println("Nomor kamar tidak ditemukan. Silakan masukkan nomor yang valid.");
                return;
            }
    
            System.out.print("Masukkan tanggal check-in (dd-MM-yyyy): ");
            String tanggalCheckIn = scanner.nextLine(); // Gunakan nextLine untuk membaca input
            System.out.print("Masukkan jumlah malam menginap: ");
            int jumlahMalam = scanner.nextInt();
            scanner.nextLine(); // Bersihkan buffer input setelah membaca angka
    
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            Date date = format.parse(tanggalCheckIn);// Mengubah string tanggal menjadi objek Date
    
            Calendar calendar = Calendar.getInstance();// Membuat objek Calendar yang dapat digunakan untuk manipulasi tanggal
            calendar.setTime(date);// Menetapkan tanggal check-in ke objek calendar
            calendar.add(Calendar.DAY_OF_MONTH, jumlahMalam);//perhitungan matemtaikan terkait menambahkan jumlah malam yang diinputkan oleh pengguna ke tanggal check-in
            
            Date tanggalCheckOut = calendar.getTime();// Mendapatkan tanggal check-out sebagai objek Date
            System.out.println("Nomor kamar: " + nomorKamar);
            System.out.println("Tanggal check-in: " + format.format(date));
            System.out.println("Tanggal check-out: " + format.format(tanggalCheckOut));
    
        } catch (ParseException e) {
        // Menangani kesalahan jika format tanggal tidak valid
            System.out.println("Format tanggal tidak valid. Gunakan format dd-MM-yyyy.");
        } catch (InputMismatchException e) {
        // Menangani kesalahan jika input bukan angka yang diharapkan
            System.out.println("Input tidak valid.");
            scanner.nextLine(); // Bersihkan buffer input jika terjadi kesalahan
        }
    }
    
    
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            koneksiDatabase();
            verifikasiPassword(scanner);
            captcha(scanner);
            
            int pilihan = -1;
            // Perulangan untuk menampilkan menu dan menangani input pilihan pengguna
            do {
                // Tampilkan menu hanya sekali per iterasi
                System.out.println("\n=== Manajemen Kamar Hotel ===");
                System.out.println("1. Tambah Kamar");
                System.out.println("2. Lihat Data Kamar");
                System.out.println("3. Perbarui Data Kamar");
                System.out.println("4. Hapus Data Kamar");
                System.out.println("5. Tanggal Check-in dan Check-out");
                System.out.println("6. Keluar");
                System.out.print("Pilih menu: ");
                
                try {  // Exception handling
                    pilihan = scanner.nextInt();
                    scanner.nextLine(); // Bersihkan buffer setelah membaca angka
                    
                    switch (pilihan) {
                        case 1 -> {
                            System.out.print("Masukkan nomor kamar: ");
                            int nomorKamar = scanner.nextInt();
                            scanner.nextLine();
                            System.out.print("Masukkan tipe kamar: ");
                            String tipeKamar = scanner.nextLine();
                            System.out.print("Masukkan harga per malam: ");
                            double hargaPerMalam = scanner.nextDouble();
                            scanner.nextLine();
                            tambahKamar(nomorKamar, tipeKamar, hargaPerMalam);
                        }
                        case 2 -> bacaKamar();
                        case 3 -> updateKamar(scanner);
                        case 4 -> {
                            System.out.print("Masukkan nomor kamar yang ingin dihapus: ");
                            int nomorHapus = scanner.nextInt();
                            scanner.nextLine();
                            hapusKamar(nomorHapus);
                        }
                        case 5 -> tanggal(scanner);
                        case 6 -> System.out.println("Terima kasih telah menggunakan sistem management hotel.");
                        default -> System.out.println("Pilihan tidak valid. Silakan coba lagi.");
                    }
                } catch (InputMismatchException e) {
                    // Exception handling untuk input yang tidak valid
                    System.out.println("Input tidak valid. Masukkan angka.");
                    scanner.nextLine(); // Bersihkan buffer untuk mencegah loop terus berjalan
                    pilihan = -1; // Mengatur ulang pilihan agar tidak keluar dari loop
                }
            } while (pilihan != 6);// Loop akan terus berjalan sampai pengguna memilih untuk keluar
            
            tutupKoneksi();
        }
    }
}
