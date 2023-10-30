public class Play {

  public String name;
  public String type;
  public static final String TRAGEDY = "tragedy";
  public static final String COMEDY = "comedy";


  private Play(String name, String type) {
    this.name = name;
    this.type = type;
  }
  // Méthode statique pour la création d'objets Play avec vérification du type
  public static Play createPlay(String name, String type) {
    // Ajoutez une vérification du type de pièce ici
    if (!type.equals(TRAGEDY) && !type.equals(COMEDY)) {
      throw new IllegalArgumentException("Invalid play type: " + type);
    }
    return new Play(name, type);
  }

}
