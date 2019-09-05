package id.ac.umn.tematik;

import java.util.ArrayList;

public class Promo {
    private Integer id;
    private String nama;
    private ArrayList<String> gambar;
    private String video;
    private String deskripsi;
    private String tanggal;
    private ArrayList<Integer> product_list;

    public Integer getId(){ return this.id; }
    public String getName(){ return this.nama; }
    public ArrayList<String> getGambar(){ return this.gambar; }
    public String getVideo(){ return this.video; }
    public String getDeskripsi(){ return this.deskripsi; }
    public String getTanggal(){ return this.tanggal; }
    public ArrayList<Integer> getProductList(){ return this.product_list; }
}
