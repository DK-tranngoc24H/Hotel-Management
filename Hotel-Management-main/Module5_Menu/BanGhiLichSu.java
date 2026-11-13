/**
 * Ban ghi dai dien cho mot phieu dat phong xuyen suot vong doi.
 */
public class BanGhiLichSu {
    private final PhieuDatPhong phieu;
    private final ChiTietHoaDon chiTietDichVu;
    private HoaDon hoaDon;
    private TrangThai trangThai;
    private String phuongThucThanhToan;

    public BanGhiLichSu(PhieuDatPhong phieu) {
        this.phieu = phieu;
        this.chiTietDichVu = new ChiTietHoaDon(phieu);
        this.trangThai = TrangThai.DAT_MOI;
    }

    public PhieuDatPhong getPhieu() {
        return phieu;
    }

    public ChiTietHoaDon getChiTietDichVu() {
        return chiTietDichVu;
    }

    public HoaDon getHoaDon() {
        return hoaDon;
    }

    public void setHoaDon(HoaDon hoaDon) {
        this.hoaDon = hoaDon;
    }

    public TrangThai getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(TrangThai trangThai) {
        this.trangThai = trangThai;
    }

    public String getPhuongThucThanhToan() {
        return phuongThucThanhToan;
    }

    public void setPhuongThucThanhToan(String phuongThucThanhToan) {
        this.phuongThucThanhToan = phuongThucThanhToan;
    }
}