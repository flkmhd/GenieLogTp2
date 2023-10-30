public class Play {

  public String name;
  public PlayType type;



  private Play(String name, PlayType type) {
    this.name = name;
    this.type = type;
  }
  // Méthode statique pour la création d'objets Play avec vérification du type
  public static Play createPlay(String name, PlayType type) {
    return new Play(name, type);
  }

  public PlayType getType() {
    return type;
  }

  public Object getName() {
    return name;
  }

}
