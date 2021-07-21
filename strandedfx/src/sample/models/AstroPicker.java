package sample.models;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class AstroPicker extends VBox {

        private ImageView checkedImage;
        private ImageView astroImage;

        private String boxChecked = "/sample/models/resources/red_boxCheckmark.png";
        private String boxCrossed = "/sample/models/resources/red_boxCross.png";

        private ASTRO astro;

        private boolean isChecked;


        public AstroPicker(ASTRO astro){
            checkedImage = new ImageView((boxCrossed));
            astroImage = new ImageView(astro.getUrl());
            this.astro = astro;
            isChecked = false;
            this.setAlignment(Pos.CENTER);
            this.setSpacing(20);
            this.getChildren().add(checkedImage);
            this.getChildren().add(astroImage);
        }

        public  ASTRO getAstro(){
            return astro;
        }

        public boolean getIsChecked(){
            return isChecked;
        }

        public void setIsBoxChecked(boolean _isChecked){
            this.isChecked = _isChecked;

            String imageToSet = this.isChecked ? boxChecked : boxCrossed;

            checkedImage.setImage(new Image(imageToSet));
        }
}
