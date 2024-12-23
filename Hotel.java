import java.util.ArrayList;// Mengimpor ArrayList dari Collection Framework

// Interface Manajemen
interface Manajemen {
    void KelolaKamar(); // Metode untuk mengelola kamar
    void lihatDetail(); // Metode untuk melihat detail
}

// Superclass Hotel yang mengimplementasikan Manajemen
class Hotel implements Manajemen {
    protected String namaHotel; // Nama hotel
    protected String alamat; // Alamat hotel
    protected ArrayList<Kamar> daftarKamar; // Daftar kamar menggunakan ArrayList

    // Constructor
    public Hotel(String namaHotel, String alamat) {
        this.namaHotel = namaHotel;
        this.alamat = alamat;
        this.daftarKamar = new ArrayList<>(); // Inisialisasi ArrayList
    }

    // Method untuk menambahkan kamar ke daftar
    public void tambahKamar(Kamar kamar) {
        daftarKamar.add(kamar);
        System.out.println("Kamar berhasil ditambahkan ke hotel " + namaHotel);// Manipulasi string untuk menggabungkan teks dengan variabel namaHotel
    }

    // Method untuk menampilkan semua kamar di hotel
    public void tampilkanKamar() {
        // Percabangan untuk mengecek apakah daftar kamar kosong
        if (daftarKamar.isEmpty()) {
            // Manipulasi string untuk menyusun pesan ketika tidak ada kamar
            System.out.println("Belum ada kamar yang terdaftar di hotel " + namaHotel);
        } else {
            // Manipulasi string untuk menampilkan judul daftar kamar
            System.out.println("Daftar kamar di hotel " + namaHotel + ":");
             // Perulangan untuk iterasi melalui semua objek Kamar di daftarKamar
            for (Kamar kamar : daftarKamar) {
                kamar.lihatDetail(); // Memanggil metode lihatDetail dari Kamar
                // Manipulasi string untuk menampilkan pemisah antar kamar
                System.out.println("-------------------------");
            }
        }
    }

    // Implementasi metode dari interface Manajemen
    @Override
    public void KelolaKamar() {
        // Manipulasi string untuk menyusun pesan terkait manajemen hotel
        System.out.println("Manajemen hotel sedang berjalan untuk " + namaHotel);
    }

    @Override
    public void lihatDetail() {
        // Manipulasi string untuk menampilkan detail hotel
        System.out.println("Detail Hotel:");
        System.out.println("Nama Hotel: " + namaHotel);// Manipulasi string untuk nama hotel
        System.out.println("Alamat: " + alamat);// Manipulasi string untuk alamat hotel
    }
}
