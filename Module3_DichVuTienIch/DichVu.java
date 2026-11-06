



import java.util.Scanner;

public abstract class DichVu {
    protected String maDV;
    protected String tenDV;
    protected double donGia;

    public DichVu() {}

    public DichVu(String maDV, String tenDV, double donGia) {
        this.maDV = maDV;
        this.tenDV = tenDV;
        this.donGia = donGia;
    }

    public String getMaDV() {
        return maDV;
    }

    public void setMaDV(String maDV) {
        this.maDV = maDV;
    }

    public String getTenDV() {
        return tenDV;
    }

    public void setTenDV(String tenDV) {
        this.tenDV = tenDV;
    }

    public double getDonGia() {
        return donGia;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }

    public void nhap() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Nhập mã dịch vụ: ");
        maDV = sc.nextLine();
        System.out.println("Nhập tên dịch vụ: ");
        tenDV = sc.nextLine();
        System.out.println("Nhập đơn giá: ");
        donGia = Double.parseDouble(sc.nextLine());
    }

    public void xuat() {
        System.out.println("Mã DV: " + maDV + " | Tên: " + tenDV + " | Đơn giá: " + donGia);
    }
}
