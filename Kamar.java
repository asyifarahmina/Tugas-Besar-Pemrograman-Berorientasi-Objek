// Subclass Kamar yang mewarisi kelas Hotel
// Kelas ini menggunakan inheritance dari superclass Hotel dan mengimplementasikan interface Manajemen
class Kamar extends Hotel implements Manajemen {
    // Variabel instance untuk menyimpan data kamar
    private final int nomorKamar; // Menyimpan nomor kamar
    private final String tipeKamar; // Menyimpan tipe kamar
    private final double hargaPerMalam; // Menyimpan harga per malam
    private boolean tersedia; // Menyimpan status ketersediaan kamar

    // Constructor
    // Constructor untuk menginisialisasi data kamar dan menggunakan constructor superclass dengan kata kunci super
    public Kamar(String namaHotel, String alamat, int nomorKamar, String tipeKamar, double hargaPerMalam) {
        super(namaHotel, alamat); // Memanggil constructor dari superclass Hotel
        this.nomorKamar = nomorKamar;
        this.tipeKamar = tipeKamar;
        this.hargaPerMalam = hargaPerMalam;
        this.tersedia = true; // Default: kamar tersedia
    }

    // Implementasi metode kelola Kamar dari interface Manajemen
    @Override
    public void KelolaKamar() {
        // Percabangan untuk mengecek ketersediaan kamar
        if (tersedia) { // Jika tersedia
            tersedia = false; // Ubah status menjadi tidak tersedia
            System.out.println("Kamar " + nomorKamar + " kamar tersedia. "); // Manipulasi String (penggabungan teks)
        } else { // Jika tidak tersedia
            System.out.println("Kamar " + nomorKamar + " sudah tidak tersedia."); // Manipulasi String
        }
    }

    // Implementasi metode lihatDetail dari interface Manajemen
    @Override
    public void lihatDetail() {
        // Menampilkan detail kamar (manipulasi String untuk penggabungan teks)
        System.out.println("Detail Kamar:");
        System.out.println("Nomor Kamar: " + nomorKamar); // Manipulasi String
        System.out.println("Tipe Kamar: " + tipeKamar); // Manipulasi String
        System.out.println("Harga per Malam: Rp " + hargaPerMalam); // Manipulasi String
        System.out.println("Tersedia: " + (tersedia ? "Ya" : "Tidak")); // Percabangan ternary untuk status ketersediaan
    }

}
