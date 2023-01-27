// This file can be replaced during build by using the `fileReplacements` array.
// `ng build` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: false,
  apiUrl:'http://localhost:8080/api',
  // apiUrl:'http://10.43.6.167:3095/api',
  firebase: {
    apiKey: "AIzaSyC1AKF1YxGB46Fn_ibpxRA8R-z_qTkh2Fk",
    authDomain: "tracc-app.firebaseapp.com",
    projectId: "tracc-app",
    storageBucket: "tracc-app.appspot.com",
    messagingSenderId: "552648146786",
    appId: "1:552648146786:web:7f2d4bc722f5a71ba8b63b",
    measurementId: "G-84627433P9"
  }
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/plugins/zone-error';  // Included with Angular CLI.
