import java.util.Scanner;

/**
 * Lớp đại diện cho tiện nghi có trong phòng (ví dụ: Tivi, Tủ lạnh, Điều hòa...)
 * Lớp này sẽ được dùng làm thuộc tính bên trong lớp Phong (Quan hệ Has-A).
 */
public class TienNghiPhong implements INhapXuat {
    private String maTienNghi;
    private boolean coTivi;          // Tiện nghi Tivi
    private boolean coTuLanh;        // Tiện nghi Tủ lạnh / Mini bar
    private boolean coDieuHoa;       // Tiện nghi Điều hòa nhiệt độ
    private boolean coBinhNongLanh;  // Tiện nghi Bình nóng lạnh
    private boolean coWifi;          // Tiện nghi Wifi internet
    private String tinhTrang;        // Tình trạng: "Tot", "Binh thuong", "Can sua", "Hong"

    // Constructor mặc định (khởi tạo phòng có các tiện nghi cơ bản)
    public TienNghiPhong() {
        this.maTienNghi = "TN01";
        this.coTivi = true;
        this.coTuLanh = false;
        this.coDieuHoa = true;
        this.coBinhNongLanh = true;
        this.coWifi = true;
        this.tinhTrang = "Tot";
    }

    // Constructor đầy đủ tham số các loại tiện nghi cụ thể
    public TienNghiPhong(String maTienNghi, boolean coTivi, boolean coTuLanh, 
                        boolean coDieuHoa, boolean coBinhNongLanh, boolean coWifi, String tinhTrang) {
        this.maTienNghi = maTienNghi;
        this.coTivi = coTivi;
        this.coTuLanh = coTuLanh;
        this.coDieuHoa = coDieuHoa;
        this.coBinhNongLanh = coBinhNongLanh;
        this.coWifi = coWifi;
        this.tinhTrang = tinhTrang;
    }

    // Constructor tương thích (hỗ trợ gọi theo mã và chuỗi mô tả cũ nếu có)
    public TienNghiPhong(String maTienNghi, String tenTienNghi, int soLuong, String tinhTrang) {
        this.maTienNghi = maTienNghi;
        this.tinhTrang = tinhTrang;
        String tenLower = (tenTienNghi != null) ? tenTienNghi.toLowerCase() : "";
        this.coTivi = tenLower.contains("tivi") || tenLower.contains("tv");
        this.coTuLanh = tenLower.contains("tu lanh") || tenLower.contains("tủ lạnh");
        this.coDieuHoa = tenLower.contains("dieu hoa") || tenLower.contains("điều hòa") || tenLower.contains("may lanh");
        this.coBinhNongLanh = tenLower.contains("nong lanh") || tenLower.contains("nóng lạnh");
        this.coWifi = true;
        // Nếu chuỗi chung chung thì bật các tiện nghi cơ bản
        if (!coTivi && !coTuLanh && !coDieuHoa && !coBinhNongLanh) {
            this.coTivi = true;
            this.coDieuHoa = true;
            this.coBinhNongLanh = true;
        }
    }

    // --- Getters và Setters ---
    public String getMaTienNghi() {
        return maTienNghi;
    }

    public void setMaTienNghi(String maTienNghi) {
        this.maTienNghi = maTienNghi;
    }

    public boolean isCoTivi() {
        return coTivi;
    }

    public void setCoTivi(boolean coTivi) {
        this.coTivi = coTivi;
    }

    public boolean isCoTuLanh() {
        return coTuLanh;
    }

    public void setCoTuLanh(boolean coTuLanh) {
        this.coTuLanh = coTuLanh;
    }

    public boolean isCoDieuHoa() {
        return coDieuHoa;
    }

    public void setCoDieuHoa(boolean coDieuHoa) {
        this.coDieuHoa = coDieuHoa;
    }

    public boolean isCoBinhNongLanh() {
        return coBinhNongLanh;
    }

    public void setCoBinhNongLanh(boolean coBinhNongLanh) {
        this.coBinhNongLanh = coBinhNongLanh;
    }

    public boolean isCoWifi() {
        return coWifi;
    }

    public void setCoWifi(boolean coWifi) {
        this.coWifi = coWifi;
    }

    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    // Đếm tổng số lượng tiện nghi mà phòng đang có
    public int getSoLuong() {
        int sl = 0;
        if (coTivi) sl++;
        if (coTuLanh) sl++;
        if (coDieuHoa) sl++;
        if (coBinhNongLanh) sl++;
        if (coWifi) sl++;
        return sl;
    }

    // Lấy chuỗi danh sách các tiện nghi đang có trong phòng
    public String getTenTienNghi() {
        StringBuilder sb = new StringBuilder();
        if (coTivi) sb.append("Tivi, ");
        if (coTuLanh) sb.append("Tu lanh, ");
        if (coDieuHoa) sb.append("Dieu hoa, ");
        if (coBinhNongLanh) sb.append("Binh nong lanh, ");
        if (coWifi) sb.append("Wifi, ");

        if (sb.length() > 0) {
            sb.setLength(sb.length() - 2); // Cắt bỏ dấu phẩy cuối cùng
            return sb.toString();
        }
        return "Khong co tien nghi";
    }

    // Kiểm tra tiện nghi còn sử dụng tốt hay không
    public boolean isConTot() {
        return "tot".equalsIgnoreCase(this.tinhTrang) || "tốt".equalsIgnoreCase(this.tinhTrang);
    }

    @Override
    public void nhap(Scanner sc) {
        this.maTienNghi = KiemTraLoi.nhapChuoiKhongRong(sc, "  + Nhap ma bo tien nghi: ");
        this.coTivi = KiemTraLoi.nhapCoKhong(sc, "  + Co Tivi khong? (co/khong): ");
        this.coTuLanh = KiemTraLoi.nhapCoKhong(sc, "  + Co Tu lanh khong? (co/khong): ");
        this.coDieuHoa = KiemTraLoi.nhapCoKhong(sc, "  + Co Dieu hoa khong? (co/khong): ");
        this.coBinhNongLanh = KiemTraLoi.nhapCoKhong(sc, "  + Co Binh nong lanh khong? (co/khong): ");
        this.coWifi = KiemTraLoi.nhapCoKhong(sc, "  + Co Wifi khong? (co/khong): ");
        this.tinhTrang = KiemTraLoi.nhapChuoiKhongRong(sc, "  + Nhap tinh trang tien nghi (Tot / Can sua / Hong): ");
    }

    @Override
    public void xuat() {
        System.out.printf("    [Tien nghi - Ma: %s]\n", maTienNghi);
        System.out.printf("      - Tivi: %-5s | Tu lanh: %-5s | Dieu hoa: %-5s\n",
                (coTivi ? "Co" : "Khong"), (coTuLanh ? "Co" : "Khong"), (coDieuHoa ? "Co" : "Khong"));
        System.out.printf("      - Binh nong lanh: %-5s | Wifi: %-5s | Tinh trang: %-8s\n",
                (coBinhNongLanh ? "Co" : "Khong"), (coWifi ? "Co" : "Khong"), tinhTrang);
    }

    @Override
    public String toString() {
        return String.format("[Tien nghi (%s): Tivi(%s), TuLanh(%s), DieuHoa(%s), NongLanh(%s), Wifi(%s) | TT: %s]",
                maTienNghi,
                (coTivi ? "Co" : "Khong"),
                (coTuLanh ? "Co" : "Khong"),
                (coDieuHoa ? "Co" : "Khong"),
                (coBinhNongLanh ? "Co" : "Khong"),
                (coWifi ? "Co" : "Khong"),
                tinhTrang);
    }
}
