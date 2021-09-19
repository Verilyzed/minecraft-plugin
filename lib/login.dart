import 'package:firstapp/main.dart';
import 'package:flutter/material.dart';

class Login {
  static TextFormField textForm(String label) {
    return TextFormField(
      decoration: InputDecoration(
        labelStyle: TextStyle(
          color: mainColor,
        ),
        focusedBorder: UnderlineInputBorder(
          borderSide: BorderSide(
            color: mainColorTheme,
            width: 2.0,
          ),
        ),
        enabledBorder: UnderlineInputBorder(
          borderSide: BorderSide(
            color: mainColorTheme,
          ),
        ),
        labelText: label,
      ),
      cursorColor: mainColor,
    );
  }

  static TextFormField textFormPassword(String label) {
    return TextFormField(
      decoration: InputDecoration(
        labelStyle: TextStyle(
          color: mainColor,
        ),
        focusedBorder: UnderlineInputBorder(
          borderSide: BorderSide(
            color: mainColorTheme,
            width: 2.0,
          ),
        ),
        enabledBorder: UnderlineInputBorder(
          borderSide: BorderSide(
            color: mainColorTheme,
          ),
        ),
        labelText: label,
      ),
      cursorColor: mainColor,
      obscureText: true,
    );
  }
}

// static TextFormField textFormPassword(Color? mainColor, String label) {
//     return TextFormField(
//       decoration: InputDecoration(
//         focusedBorder: OutlineInputBorder(
//           borderRadius: BorderRadius.circular(25),
//           borderSide: const BorderSide(
//             color: mainColorTheme,
//           ),
//         ),
//         enabledBorder: OutlineInputBorder(
//           borderRadius: BorderRadius.circular(25.0),
//           borderSide: const BorderSide(
//             color: mainColorTheme,
//             width: 2.0,
//           ),
//         ),
//         labelText: label,
//       ),
//       cursorColor: mainColor,
//       obscureText: true,
//     );
//   }