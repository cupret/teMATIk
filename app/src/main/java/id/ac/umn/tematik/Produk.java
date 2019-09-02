package id.ac.umn.tematik;

import java.util.ArrayList;

public class Produk {
    private Integer id;
    private String nama;
    private ArrayList<String> gambar;
    private String video;
    private String deskripsi;
    private boolean lapisan_rodhium;
    private Integer estimasi_berat;
    private ArrayList<Spesifikasi_berlian> spesifikasi_berlian;

    public Produk(Integer id, String nama, String deskripsi, boolean lapisan_rodhium, Integer estimasi_berat){
        this.id = id;
        this.nama = nama;
        this.deskripsi = deskripsi;
        this.lapisan_rodhium = lapisan_rodhium;
        this.estimasi_berat = estimasi_berat;
    }

    public Integer getId(){ return this.id; }
    public String getName(){ return this.nama; }
    public ArrayList<String> getGambar(){ return this.gambar; }
    public String getVideo(){ return this.video; }
    public String getDeskripsi(){ return this.deskripsi; }
    public boolean getLapisanRodhium(){ return this.lapisan_rodhium; }
    public Integer getSstimasiBerat(){ return this.estimasi_berat; }
    public ArrayList<Spesifikasi_berlian> getSpesifikasiBerlian(){ return this.spesifikasi_berlian; }

    public class Spesifikasi_berlian{
        private String nama;
        private String bentuk;
        private Integer jumlah;
        private Integer berat_carat;
        private boolean cut;
        private String color;
        private String clarity;

        public String getName(){ return this.nama; }
        public String getBentuk(){ return this.bentuk; }
        public Integer getJumlah(){ return this.jumlah; }
        public Integer getBeratCarat(){ return this.berat_carat; }
        public boolean getCut(){ return this.cut; }
        public String getColor(){ return this.color; }
        public String getClarity(){ return this.clarity; }
    }

}
