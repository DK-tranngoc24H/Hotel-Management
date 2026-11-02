import java.util.ArrayList;
import java.util.Scanner;

/**
 * Lớp kiểm thử (Test/Main) chứng minh 4 class nghiệp vụ hoạt động hoàn hảo.
 * Thể hiện rõ các trụ cột của OOP: Đóng gói, Kế thừa, Trừu tượng, Đa hình.
 */
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Phong> danhSachPhong = new ArrayList<>();

        System.out.println("===============================================================");
        System.out.println("     CHUONG TRINH QUAN LY PHONG KHACH SAN (DEMO OOP)          ");
        System.out.println("===============================================================\n");

        // 1. KHỞI TẠO DỮ LIỆU MẪU ĐỂ TEST NHANH (Không cần gõ tay nhiều)
        TienNghiPhong tn1 = new TienNghiPhong("TN01", "Dieu hoa 2 chieu", 1, "Tot");
        TienNghiPhong tn2 = new TienNghiPhong("TN02", "Smart Tivi 65 inch", 1, "Tot");

        danhSachPhong.add(new PhongStandard("STD101", 450000, "Trong", true, tn1));
        danhSachPhong.add(new PhongVIP("VIP201", 1200000, "Dang o", "Bien", 200000, tn2));

        // 2. CHO PHEP NHAP THEM 1 PHONG TU BAN PHIM (Neu nguoi dung muon)
        System.out.print("Ban co muon nhap them 1 phong tu ban phim khong? (co/khong): ");
        String chonNhap = sc.nextLine().trim();

        if (chonNhap.equalsIgnoreCase("co") || chonNhap.equalsIgnoreCase("có") || chonNhap.equals("1")) {
            System.out.print("Chon loai phong (1: Standard | 2: VIP): ");
            int loai = sc.nextInt();
            sc.nextLine(); // Chống trôi dòng

            if (loai == 1) {
                System.out.println("\n--- Nhap phong Standard ---");
                PhongStandard ps = new PhongStandard();
                ps.nhap(sc);
                danhSachPhong.add(ps);
            } else if (loai == 2) {
                System.out.println("\n--- Nhap phong VIP ---");
                PhongVIP pv = new PhongVIP();
                pv.nhap(sc);
                danhSachPhong.add(pv);
            } else {
                System.out.println("Lua chon khong hop le, bo qua phan nhap.");
            }
        }

        // 3. THE HIEN TINH DA HINH (POLYMORPHISM) - XUAT TAT CA CAC PHONG
        System.out.println("\n===============================================================");
        System.out.println("                   DANH SACH TAT CA CAC PHONG                 ");
        System.out.println("===============================================================");
        for (Phong p : danhSachPhong) {
            p.xuat(); // Java tu dong goi xuat() cua lop con tuong ung
            System.out.println("---------------------------------------------------------------");
        }

        // 4. LOC CAC PHONG CON TRONG
        System.out.println("\n===============================================================");
        System.out.println("                    DANH SACH PHONG CON TRONG                  ");
        System.out.println("===============================================================");
        int countTrong = 0;
        for (Phong p : danhSachPhong) {
            if (p.conTrong()) {
                System.out.printf("- Ma phong: %-8s | Loai: %-12s | Gia: %,.0f VND\n",
                        p.getMaPhong(),
                        (p instanceof PhongVIP ? "VIP" : "Standard"),
                        p.tinhGiaPhong());
                countTrong++;
            }
        }
        if (countTrong == 0) {
            System.out.println("Hien tai khong con phong nao trong.");
        }

        // 5. TINH TONG DOANH THU DU KIEN
        double tongDoanhThu = 0;
        for (Phong p : danhSachPhong) {
            tongDoanhThu += p.tinhGiaPhong();
        }
        System.out.println("\n===============================================================");
        System.out.printf(" >> TONG DOANH THU DU KIEN: %,.0f VND\n", tongDoanhThu);
        System.out.println("===============================================================");

        sc.close();
    }
}
