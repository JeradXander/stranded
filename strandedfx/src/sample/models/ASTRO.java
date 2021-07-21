package sample.models;

public enum ASTRO {

    //enum values
    SOLDIER("/sample/models/resources/soldier_img.png"),
    MEDIC("/sample/models/resources/medic_img.png"),
    EXPLORER("/sample/models/resources/explorer_img.png");

    String urlAstro;

    //constructor
     ASTRO(String urlAstro){
        this.urlAstro = urlAstro;
    }

    public String getUrl(){
        return this.urlAstro;
    }
}
