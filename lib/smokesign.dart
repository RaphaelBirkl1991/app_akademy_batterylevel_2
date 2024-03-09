class SmokeSign {
  String name;

  SmokeSign(this.name);

  Map<String, dynamic> toMap() {
    return {"name": name};
  }

  factory SmokeSign.fromMap(Map<String, dynamic> map) {
    return SmokeSign(map["name"]);
  }
}
