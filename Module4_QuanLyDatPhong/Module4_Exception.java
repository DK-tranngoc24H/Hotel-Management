/**
 * Lớp ngoại lệ tổng hợp cho Module 4 (Quản lý đặt phòng & hóa đơn).
 */
public class Module4_Exception extends RuntimeException {

    public Module4_Exception(String message) {
        super(message);
    }

    public Module4_Exception(String message, Throwable cause) {
        super(message, cause);
    }

    public static class NgayThangKhongHopLeException extends Module4_Exception {
        public NgayThangKhongHopLeException(String message) {
            super(message);
        }
    }

    public static class PhieuDatPhongException extends Module4_Exception {
        public PhieuDatPhongException(String message) {
            super(message);
        }
    }

    public static class PhongKhongKhaDungException extends Module4_Exception {
        public PhongKhongKhaDungException(String message) {
            super(message);
        }
    }

    public static class DichVuKhongHopLeException extends Module4_Exception {
        public DichVuKhongHopLeException(String message) {
            super(message);
        }
    }

    public static class HoaDonKhongHopLeException extends Module4_Exception {
        public HoaDonKhongHopLeException(String message) {
            super(message);
        }
    }

    public static class ThanhToanKhongHopLeException extends Module4_Exception {
        public ThanhToanKhongHopLeException(String message) {
            super(message);
        }
    }

    public static class DuLieuFileException extends Module4_Exception {
        public DuLieuFileException(String message) {
            super(message);
        }

        public DuLieuFileException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
