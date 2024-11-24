import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static ArrayList<Menu> menuList = new ArrayList<>();
    private static ArrayList<Menu> pesanan = new ArrayList<>();
    private static ArrayList<Integer> jumlahPesanan = new ArrayList<>();

    public static void main(String[] args) {
        // Inisialisasi menu
        initMenu();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n=== Aplikasi Restoran ===");
            System.out.println("1. Menu Pelanggan");
            System.out.println("2. Manajemen Menu");
            System.out.println("3. Keluar");
            System.out.print("Pilih: ");
            int pilihan = scanner.nextInt();
            scanner.nextLine();

            switch (pilihan) {
                case 1:
                    menuPelanggan(scanner);
                    break;
                case 2:
                    manajemenMenu(scanner);
                    break;
                case 3:
                    System.out.println("Terima kasih telah menggunakan aplikasi restoran!");
                    return;
                default:
                    System.out.println("Pilihan tidak valid. Silakan coba lagi.");
            }
        }
    }

    private static void initMenu() {
        menuList.add(new Menu("Nasi Goreng", 20000, "Makanan"));
        menuList.add(new Menu("Ayam Goreng", 25000, "MakanaNasi Gorengn"));
        menuList.add(new Menu("Mie Ayam", 18000, "Makanan"));
        menuList.add(new Menu("Soto Ayam", 22000, "Makanan"));
        menuList.add(new Menu("Es Teh", 5000, "Minuman"));
        menuList.add(new Menu("Es Jeruk", 7000, "Minuman"));
        menuList.add(new Menu("Kopi", 10000, "Minuman"));
        menuList.add(new Menu("Jus Alpukat", 15000, "Minuman"));
    }

    private static void menuPelanggan(Scanner scanner) {
        pesanan.clear();
        jumlahPesanan.clear();

        while (true) {
            System.out.println("\n=== Daftar Menu ===");
            tampilkanMenu();

            System.out.println("Masukkan nama menu yang ingin dipesan ('selesai' untuk mengakhiri):");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("selesai")) break;

            Menu menu = cariMenu(input);
            if (menu != null) {
                System.out.print("Masukkan jumlah: ");
                int jumlah = scanner.nextInt();
                scanner.nextLine();

                pesanan.add(menu);
                jumlahPesanan.add(jumlah);
            } else {
                System.out.println("Menu tidak ditemukan. Silakan coba lagi.");
            }
        }

        hitungTotalPesanan();
    }

    private static void tampilkanMenu() {
        System.out.println("Kategori: Makanan");
        for (Menu menu : menuList) {
            if (menu.getKategori().equalsIgnoreCase("Makanan")) {
                System.out.printf("%s - Rp%.2f\n", menu.getNama(), menu.getHarga());
            }
        }

        System.out.println("Kategori: Minuman");
        for (Menu menu : menuList) {
            if (menu.getKategori().equalsIgnoreCase("Minuman")) {
                System.out.printf("%s - Rp%.2f\n", menu.getNama(), menu.getHarga());
            }
        }
    }

    private static Menu cariMenu(String nama) {
        for (Menu menu : menuList) {
            if (menu.getNama().equalsIgnoreCase(nama)) {
                return menu;
            }
        }
        return null;
    }

    private static void hitungTotalPesanan() {
        double total = 0;
        double diskon = 0;
        boolean dapatDiskonMinuman = false;

        System.out.println("\n=== Struk Pesanan ===");
        for (int i = 0; i < pesanan.size(); i++) {
            Menu menu = pesanan.get(i);
            int jumlah = jumlahPesanan.get(i);
            double hargaTotal = menu.getHarga() * jumlah;

            System.out.printf("%s x%d - Rp%.2f\n", menu.getNama(), jumlah, hargaTotal);
            total += hargaTotal;

            // Penawaran beli satu gratis satu untuk minuman
            if (menu.getKategori().equalsIgnoreCase("Minuman") && total > 50000) {
                dapatDiskonMinuman = true;
            }
        }

        System.out.printf("Total Harga: Rp%.2f\n", total);
        if (total > 100000) {
            diskon = total * 0.1;
            System.out.printf("Diskon 10%%: -Rp%.2f\n", diskon);
        }

        if (dapatDiskonMinuman) {
            System.out.println("Beli 1 Gratis 1 Minuman Tiap Pembelian diatas 50 Ribu!");
        }

        total -= diskon;
        double pajak = total * 0.1;
        total += pajak + 20000;

        System.out.printf("Pajak (10%%): Rp%.2f\n", pajak);
        System.out.println("Biaya Pelayanan: Rp20000");
        System.out.printf("Total Pembayaran: Rp%.2f\n", total);
    }

    private static void manajemenMenu(Scanner scanner) {
        while (true) {
            System.out.println("\n=== Manajemen Menu ===");
            System.out.println("1. Tambah Menu");
            System.out.println("2. Ubah Harga Menu");
            System.out.println("3. Hapus Menu");
            System.out.println("4. Kembali");
            System.out.print("Pilih: ");
            int pilihan = scanner.nextInt();
            scanner.nextLine();

            switch (pilihan) {
                case 1:
                    tambahMenu(scanner);
                    break;
                case 2:
                    ubahHargaMenu(scanner);
                    break;
                case 3:
                    hapusMenu(scanner);
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Pilihan tidak valid. Silakan coba lagi.");
            }
        }
    }

    private static void tambahMenu(Scanner scanner) {
        System.out.print("Masukkan nama menu: ");
        String nama = scanner.nextLine();
        System.out.print("Masukkan harga menu: ");
        double harga = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Masukkan kategori (Makanan/Minuman): ");
        String kategori = scanner.nextLine();

        menuList.add(new Menu(nama, harga, kategori));
        System.out.println("Menu berhasil ditambahkan!");
    }

    private static void ubahHargaMenu(Scanner scanner) {
        tampilkanMenu();
        System.out.print("Masukkan nama menu yang ingin diubah: ");
        String nama = scanner.nextLine();

        Menu menu = cariMenu(nama);
        if (menu != null) {
            System.out.print("Masukkan harga baru: ");
            double harga = scanner.nextDouble();
            scanner.nextLine();
            menu.setHarga(harga);
            System.out.println("Harga menu berhasil diubah!");
        } else {
            System.out.println("Menu tidak ditemukan.");
        }
    }

    private static void hapusMenu(Scanner scanner) {
        tampilkanMenu();
        System.out.print("Masukkan nama menu yang ingin dihapus: ");
        String nama = scanner.nextLine();

        Menu menu = cariMenu(nama);
        if (menu != null) {
            System.out.print("Apakah Anda yakin ingin menghapus menu ini? (Ya/Tidak): ");
            String konfirmasi = scanner.nextLine();
            if (konfirmasi.equalsIgnoreCase("Ya")) {
                menuList.remove(menu);
                System.out.println("Menu berhasil dihapus!");
            } else {
                System.out.println("Penghapusan menu dibatalkan.");
            }
        } else {
            System.out.println("Menu tidak ditemukan.");
        }
    }
}