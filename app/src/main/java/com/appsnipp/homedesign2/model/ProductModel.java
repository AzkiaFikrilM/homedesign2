package com.appsnipp.homedesign2.model;

public class ProductModel {

    private String fotoProduk, namaProduk, deskripsiProduk, kategoriProduk;

    public ProductModel() {

    }

    public ProductModel(String fotoProduk, String namaProduk, String deskripsiProduk, String kategoriProduk) {
        this.fotoProduk = fotoProduk;
        this.namaProduk = namaProduk;
        this.deskripsiProduk = deskripsiProduk;
        this.kategoriProduk = kategoriProduk;
    }

    public String getFotoProduk() {
        return fotoProduk;
    }

    public void setFotoProduk(String fotoProduk) {
        this.fotoProduk = fotoProduk;
    }

    public String getNamaProduk() {
        return namaProduk;
    }

    public void setNamaProduk(String namaProduk) {
        this.namaProduk = namaProduk;
    }

    public String getDeskripsiProduk() {
        return deskripsiProduk;
    }

    public void setDeskripsiProduk(String deskripsiProduk) {
        this.deskripsiProduk = deskripsiProduk;
    }

    public String getKategoriProduk() {
        return kategoriProduk;
    }

    public void setKategoriProduk(String kategoriProduk) {
        this.kategoriProduk = kategoriProduk;
    }
}
