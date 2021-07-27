//package sample.volume;
//
//public class playMedia  {
//
//    beginTimer();
//    changeSpeed(null);
//		gameMediaPlayer.setVolume(volumeSlider.getValue() * 0.01);
//		gameMediaPlayer.play();
//}
//
//    public void pauseMedia() {
//
//        cancelTimer();
//        gameMediaPlayer.pause();
//    }
//
//    public void resetMedia() {
//
//        songProgressBar.setProgress(0);
//        gameMediaPlayer.seek(Duration.seconds(0));
//    }
//
//    public void previousMedia() {
//
//        if(songNumber > 0) {
//
//            songNumber--;
//
//            gameMediaPlayer.stop();
//
//            if(running) {
//
//                cancelTimer();
//            }
//
//            media = new Media(songs.get(songNumber).toURI().toString());
//            gameMediaPlayer = new MediaPlayer(media);
//
//            songLabel.setText(songs.get(songNumber).getName());
//
//            playMedia();
//        }
//        else {
//
//            songNumber = songs.size() - 1;
//
//            gameMediaPlayer.stop();
//
//            if(running) {
//
//                cancelTimer();
//            }
//
//            media = new Media(songs.get(songNumber).toURI().toString());
//            gameMediaPlayer = new MediaPlayer(media);
//
//            songLabel.setText(songs.get(songNumber).getName());
//
//            playMedia();
//        }
//    }
//
//    public void nextMedia() {
//
//        if(songNumber < songs.size() - 1) {
//
//            songNumber++;
//
//            gameMediaPlayer.stop();
//
//            if(running) {
//
//                cancelTimer();
//            }
//
//            media = new Media(songs.get(songNumber).toURI().toString());
//            gameMediaPlayer = new MediaPlayer(media);
//
//            songLabel.setText(songs.get(songNumber).getName());
//
//            playMedia();
//        }
//        else {
//
//            songNumber = 0;
//
//            gameMediaPlayer.stop();
//
//            media = new Media(songs.get(songNumber).toURI().toString());
//            gameMediaPlayer = new MediaPlayer(media);
//
//            songLabel.setText(songs.get(songNumber).getName());
//
//            playMedia();
//        }
//    }
//
//    public void changeSpeed(ActionEvent event) {
//
//        if(speedBox.getValue() == null) {
//
//            gameMediaPlayer.setRate(1);
//        }
//        else {
//
//            //gameMediaPlayer.setRate(Integer.parseInt(speedBox.getValue()) * 0.01);
//            gameMediaPlayer.setRate(Integer.parseInt(speedBox.getValue().substring(0, speedBox.getValue().length() - 1)) * 0.01);
//        }
//    }
//
//    public void beginTimer() {
//
//        timer = new Timer();
//
//        task = new TimerTask() {
//
//            public void run() {
//
//                running = true;
//                double current = gameMediaPlayer.getCurrentTime().toSeconds();
//                double end = media.getDuration().toSeconds();
//                songProgressBar.setProgress(current/end);
//
//                if(current/end == 1) {
//
//                    cancelTimer();
//                }
//            }
//        };
//
//        timer.scheduleAtFixedRate(task, 0, 1000);
//    }
//
//    public void cancelTimer() {
//
//        running = false;
//        timer.cancel();
//    }
//}
//
