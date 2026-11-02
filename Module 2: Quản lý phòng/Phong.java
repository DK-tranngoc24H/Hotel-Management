import java.util.Scanner;

public abstract class Phong implements nhapxuat {
    private String maphong;
    private double giacoban;
    private String trangthai;
    private TienNghiPhong tienNghi;

    public Phong() {
        this.maphong = "";
        this.giacoban = 0;
        this.trangthai = "";
        this.tienNghi = new TienNghiPhong();
    }

    public Phong(String maphong, double giacoban, String trangthai, TienNghiPhong tienNghi) {
        this.maphong = maphong;
        this.giacoban = giacoban;
        this.trangthai = trangthai;
        this.tienNghi = tienNghi;
    }

    public String getmaphong() {
        return maphong;
    }

    public void setmaphong(String maphong) {
        this.maphong = maphong;
    }

    public double getgiacoban() {
        return giacoban;
    }

    public void setgiacoban(double giacoban) {
        this.giacoban = giacoban;
    }

    public String gettrangthai() {
        return trangthai;
    }

    public void settrangthai(String trangthai) {
        this.trangthai = trangthai;
    }

    public TienNghiPhong gettienNghi() {
        return tienNghi;
    }

    public void setTienNghi(TienNghiPhong tienNghi) {
        this.tienNghi = tienNghi;
    }

    public boolean controng() {
        if ("trong".equalsIgnoreCase(this.trangthai)) {
            return true;
        } else {
            return false;
        }

    }

    public abstract double tinhgiaphong();

    public void nhap(Scanner sc) {
        System.out.println("nhap ma phong");
        this.maphong = sc.nextLine();
        System.out.println("nha gia co ban");
        this.giacoban = sc.nextDouble();
        sc.nextLine();
        System.out.println("trang thai:");
        this.trangthai = sc.nextLine();
    }

    public void xuat() {
        System.out.printf("ma phong la: %-8s| gia co ban: %-10.2f| trang thai phong: %-8s", maphong, giacoban,
                trangthai);

    }
}