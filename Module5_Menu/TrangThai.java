/**
 * CLASS TINH 1: Quan ly tap hang so trang thai va tien ich kiem tra luong nghiep vu.
 */
public enum TrangThai {
    DAT_MOI("Dat moi"),
    DANG_O("Dang o"),
    DA_TRA_PHONG("Da tra phong"),
    DA_HUY("Da huy");

    private final String tenHienThi;

    TrangThai(String tenHienThi) {
        this.tenHienThi = tenHienThi;
    }

    public String getTenHienThi() {
        return tenHienThi;
    }

    /** Phuong thuc tinh kiem tra xem ban ghi co con hieu luc xu ly (chua ket thuc) hay khong. */
    public static boolean coTheTacDong(TrangThai trangThai) {
        return trangThai == DAT_MOI || trangThai == DANG_O;
    }

    /** Phuong thuc tinh in duong ke phan cach giao dien chung. */
    public static void inDuongKe(int doDai) {
        System.out.println("-".repeat(Math.max(0, doDai)));
    }
}