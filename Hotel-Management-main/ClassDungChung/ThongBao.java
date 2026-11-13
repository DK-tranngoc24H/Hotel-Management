/**
 * Lớp quản lý và hiển thị thông điệp dùng chung (lỗi, thành công, cảnh báo, tiêu đề).
 */
public class ThongBao {

    // Các hằng số thông báo lỗi dùng chung
    public static final String LOI_DE_TRONG        = "Du lieu khong duoc de trong! Vui long nhap lai.";
    public static final String LOI_SO_AM           = "Gia tri phai lon hon hoac bang 0!";
    public static final String LOI_SO_KHONG_HOP_LE = "Dinh dang so khong hop le!";
    public static final String LOI_CHON_CO_KHONG   = "Vui long chi chon 'co' hoac 'khong'!";
    public static final String LOI_TRANG_THAI      = "Trang thai chi duoc la: Trong, Dang o, hoac Da dat!";
    public static final String LOI_VIEW            = "Loai view chi duoc la: Bien, Thanh pho, hoac San vuon!";

    public static void inLoi(String noiDung) {
        System.out.println("  [!] LOI: " + noiDung);
    }

    public static void inThanhCong(String noiDung) {
        System.out.println("  [v] THANH CONG: " + noiDung);
    }

    public static void inCanhBao(String noiDung) {
        System.out.println("  [*] CANH BAO: " + noiDung);
    }

    public static void inTieuDe(String tieuDe) {
        System.out.println("\n========================================");
        System.out.println("   " + tieuDe.toUpperCase());
        System.out.println("========================================");
    }
}
