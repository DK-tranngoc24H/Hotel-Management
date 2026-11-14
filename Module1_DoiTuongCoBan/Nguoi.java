import java.util.Scanner;

public abstract class Nguoi {
    Scanner sc = new Scanner(System.in);
    protected String hoten;
    protected String CCCD;
    protected String sdt;
    protected String diachi;

    // Thanh phan static dem so luong nguoi da duoc tao (Buoc 5)
    public static int soLuongNguoi = 0;

    public Nguoi() {
        this.hoten = "";
        this.CCCD = "";
        this.sdt = "";
        this.diachi = "";
        soLuongNguoi++;
    }

    public Nguoi(String hoten, String CCCD, String sdt, String diachi) {
        this.hoten = "";
        this.CCCD = "";
        this.sdt = "";
        this.diachi = "";
        setHoten(hoten);
        setCCCD(CCCD);
        setSdt(sdt);
        setDiachi(diachi);
        soLuongNguoi++;
    }

    public static boolean kiemTraHoTen(String hoten) {
        return hoten != null && hoten.trim().matches("^[\\p{L}\\s]+$");
    }

    public static boolean kiemTraCCCD(String cccd) {
        return cccd != null && cccd.trim().matches("^\\d{9}(\\d{3})?$");
    }

    public static boolean kiemTraSDT(String sdt) {
        return sdt != null && sdt.trim().matches("^0\\d{9}$");
    }

    public void nhap() {
        while (true) {
            hoten = KiemTraLoi.nhapChuoiKhongRong(sc, "Nhap Ho va Ten: ");
            if (kiemTraHoTen(hoten)) {
                break;
            }
            ThongBao.inLoi("Ho va ten chi duoc chua chu cai va khoang trang!");
        }

        while (true) {
            CCCD = KiemTraLoi.nhapChuoiKhongRong(sc, "Nhap CCCD (9 hoac 12 chu so): ");
            if (kiemTraCCCD(CCCD)) {
                break;
            }
            ThongBao.inLoi("CCCD khong hop le! Phai gom 9 hoac 12 chu so.");
        }

        while (true) {
            sdt = KiemTraLoi.nhapChuoiKhongRong(sc, "Nhap sdt (10 so, bat dau bang 0): ");
            if (kiemTraSDT(sdt)) {
                break;
            }
            ThongBao.inLoi("So dien thoai khong hop le! Phai gom 10 chu so va bat dau bang so 0.");
        }

        while (true) {
            diachi = KiemTraLoi.nhapChuoiKhongRong(sc, "Nhap Dia Chi: ");
            if (!diachi.contains(";")) {
                break;
            }
            ThongBao.inLoi("Dia chi khong duoc chua ky tu ';'!");
        }
    }

    public void xuat() {
        System.out.println("Ho va Ten: " + hoten + " | CCCD: " + CCCD + " | SDT: " + sdt + " | Dia Chi: " + diachi);
    }

    // Phuong thuc truu tuong hoac da hinh tra ve ma dinh danh (CCCD hoac MaNV)
    public String getMaDinhDanh() {
        return CCCD;
    }

    public String gethoten() {
        return hoten;
    }

    public String getCCCD() {
        return CCCD;
    }

    public String getSdt() {
        return sdt;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setHoten(String hoten) {
        if (hoten == null || hoten.trim().isEmpty()) {
            ThongBao.inLoi("Ho va ten khong duoc de trong!");
            return;
        }
        if (!kiemTraHoTen(hoten)) {
            ThongBao.inLoi("Ho va ten chi duoc chua chu cai va khoang trang!");
            return;
        }
        this.hoten = hoten.trim();
    }

    public void setCCCD(String cCCD) {
        if (!kiemTraCCCD(cCCD)) {
            ThongBao.inLoi("CCCD khong hop le! Phai gom 9 hoac 12 chu so.");
            return;
        }
        this.CCCD = cCCD.trim();
    }

    public void setSdt(String sdt) {
        if (!kiemTraSDT(sdt)) {
            ThongBao.inLoi("So dien thoai khong hop le! Phai gom 10 chu so va bat dau bang so 0.");
            return;
        }
        this.sdt = sdt.trim();
    }

    public void setDiachi(String diachi) {
        if (diachi == null || diachi.trim().isEmpty()) {
            ThongBao.inLoi("Dia chi khong duoc de trong!");
            return;
        }
        if (diachi.contains(";")) {
            ThongBao.inLoi("Dia chi khong duoc chua ky tu ';'!");
            return;
        }
        this.diachi = diachi.trim();
    }
}
