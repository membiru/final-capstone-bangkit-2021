# final-capstone-bangkit-2021
Named "Whatdish" is application based on machine learning to recognize calories of the food. Purpose to fulfill final project Bangkit 2021.

## Description
The app will display the results of the object in the form of images taken by the user from the camera or from their gallery. From the image it will be sent to firebase storage for another processing in the cloud and if the process is successful the results will be displayed to the app.

## Instruction
### For UI 
Create Splash Screen, like on here
```
final-capstone-bangkit-2021/app/src/main/java/com/bangkit/whatdish/ui/splash/...
```
and also Intro Slider
```
final-capstone-bangkit-2021/app/src/main/java/com/bangkit/whatdish/ui/intro/...
```

### Access Camera and Gallery
Because the app need image from camera or gallery so need to access that, then upload that to firebase storage to be processed in cloud, like on here
```
final-capstone-bangkit-2021/app/src/main/java/com/bangkit/whatdish/ui/main/...
```

### Detail Result
And the last, after upload image that captured then the app will show the result get from API
```
final-capstone-bangkit-2021/app/src/main/java/com/bangkit/whatdish/ui/detail/...
```

## Built With

* [Retrofit2] - The loader API
* [Firebase]- As a storage
* [ViewPager2] - To use for slider intro
* [Picasso] - Image loader
