import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

import 'login.dart';

void main() => runApp(const MyApp());

final mainColor = Colors.orange[800];
final mainColorTheme = Colors.orange;

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    SystemChrome.setSystemUIOverlayStyle(
      const SystemUiOverlayStyle(
        statusBarColor: Colors.black,
      ),
    );

    return MaterialApp(
      title: "Main",
      theme: ThemeData(
        disabledColor: mainColor,
        primaryColor: mainColor,
        primarySwatch: mainColorTheme,
        textTheme: const TextTheme(
          bodyText1: TextStyle(),
          bodyText2: TextStyle(),
          button: TextStyle(),
          caption: TextStyle(),
          headline1: TextStyle(),
          headline2: TextStyle(),
          headline3: TextStyle(),
          headline4: TextStyle(),
          headline5: TextStyle(),
          headline6: TextStyle(),
          subtitle1: TextStyle(),
          subtitle2: TextStyle(),
          overline: TextStyle(),
        ).apply(
          bodyColor: mainColor,
          displayColor: mainColor,
        ),
      ),
      home: const MainScreen(),
    );
  }
}

class MainScreen extends StatefulWidget {
  const MainScreen({Key? key}) : super(key: key);

  @override
  State<MainScreen> createState() => _MainScreenState();
}

class _MainScreenState extends State<MainScreen> {
  int _selectedIndex = 0;
  var pages = ["Home", "New", "Login", "Settings"];
  String page = "Home";

  final controller = PageController(
    initialPage: 0,
  );

  void _onItemTapped(int index) {
    setState(() {
      _selectedIndex = index;
      page = pages[_selectedIndex];

      controller.animateToPage(_selectedIndex,
          duration: const Duration(milliseconds: 250), curve: Curves.easeInOut);
    });
  }

  void _onPageSwipe(int index) {
    setState(() {
      _selectedIndex = index;
      page = pages[_selectedIndex];
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        titleTextStyle: TextStyle(
          color: mainColor,
          fontSize: 20,
        ),
        backgroundColor: Colors.black,
        title: Text(page),
        actions: [
          IconButton(
            onPressed: () {},
            icon: const Icon(Icons.verified_user),
          ),
          IconButton(
            onPressed: () {},
            icon: const Icon(Icons.supervised_user_circle),
          ),
        ],
      ),
      body: PageView(
        controller: controller,
        scrollDirection: Axis.horizontal,
        physics: const ClampingScrollPhysics(),
        children: [
          HomePage(),
          NewPage(),
          LoginPage(),
          SettingsPage(),
        ],
        onPageChanged: _onPageSwipe,
      ),
      bottomNavigationBar: _bottomNavigationBar(),
      backgroundColor: Colors.black,
    );
  }

  BottomNavigationBar _bottomNavigationBar() {
    return BottomNavigationBar(
      items: const <BottomNavigationBarItem>[
        BottomNavigationBarItem(
          icon: Icon(Icons.home),
          label: "Home",
        ),
        BottomNavigationBarItem(
          icon: Icon(Icons.plus_one_rounded),
          label: "New",
        ),
        BottomNavigationBarItem(
          icon: Icon(Icons.login),
          label: "Login",
        ),
        BottomNavigationBarItem(
          icon: Icon(Icons.settings),
          label: 'Settings',
        ),
      ],
      backgroundColor: Colors.black,
      type: BottomNavigationBarType.fixed,
      unselectedItemColor: Colors.white,
      selectedItemColor: mainColor,
      currentIndex: _selectedIndex,
      onTap: _onItemTapped,
    );
  }
}

// ignore: non_constant_identifier_names
Padding HomePage() {
  return const Padding(
    padding: EdgeInsets.all(12),
    child: Text("Text"),
  );
}

// ignore: non_constant_identifier_names
Padding NewPage() {
  return const Padding(
    padding: EdgeInsets.all(12),
    child: Text("New"),
  );
}

// ignore: non_constant_identifier_names
Widget LoginPage() {
  Widget titleSection = Container(
    padding: const EdgeInsets.all(32),
    child: Row(
      children: [
        Expanded(
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Container(
                padding: const EdgeInsets.only(bottom: 8),
                child: Login.textForm("Username"),
              ),
              Container(
                padding: const EdgeInsets.only(bottom: 8),
                child: Login.textFormPassword("Password"),
              ),
            ],
          ),
        ),
      ],
    ),
  );

  return titleSection;
}

// ignore: non_constant_identifier_names
ListView SettingsPage() {
  var items = [
    TextButton.icon(
        onPressed: () {},
        style: TextButton.styleFrom(primary: mainColor),
        icon: const Icon(Icons.settings_applications_rounded),
        label: const Text("App Settings")),
    TextButton.icon(
        onPressed: () {},
        style: TextButton.styleFrom(primary: mainColor),
        icon: const Icon(Icons.security),
        label: const Text("Security")),
    TextButton.icon(
        onPressed: () {},
        style: TextButton.styleFrom(primary: mainColor),
        icon: const Icon(Icons.attach_money_sharp),
        label: const Text("Donation")),
    TextButton.icon(
        onPressed: () {},
        style: TextButton.styleFrom(primary: mainColor),
        icon: const Icon(Icons.question_answer_rounded),
        label: const Text("FAQ")),
    TextButton.icon(
        onPressed: () {},
        style: TextButton.styleFrom(primary: mainColor),
        icon: const Icon(Icons.support_outlined),
        label: const Text("Support")),
    TextButton.icon(
        onPressed: () {},
        style: TextButton.styleFrom(primary: mainColor),
        icon: const Icon(Icons.info_outline),
        label: const Text("Info")),
  ];

  return ListView(
    children: items,
  );
}
