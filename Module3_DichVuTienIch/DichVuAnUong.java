



import java.util.Scanner;

public class DichVuAnUong extends DichVu {
    private String phanLoai; 
    private String chiTiet;  

    public DichVuAnUong() {
        super();
    }

    public DichVuAnUong(String maDV, String tenDV, double donGia, String phanLoai, String chiTiet) {
        super(maDV, tenDV, donGia);
        this.phanLoai = phanLoai;
        this.chiTiet = chiTiet;
    }

    @Override
    public void nhap() {
        super.nhap();
        Scanner sc = new Scanner(System.in);
        System.out.println("Phân loại (Đồ ăn / Nước uống): ");
        phanLoai = sc.nextLine();
        System.out.println("Chi tiết (VD: Bữa sáng / Nước ngọt 330ml): ");
        chiTiet = sc.nextLine();
    }

    @Override
    public void xuat() {
        super.xuat();
        System.out.println(" Loại: " + phanLoai + " | Chi tiết: " + chiTiet);
    }
}
