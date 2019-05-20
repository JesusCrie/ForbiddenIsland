# Styleguide

 1. [Nommage](#nommage)
	 1. [Classes / Interfaces](#classes--interfaces)
	 2. [Méthodes](#méthodes)
	 3. [Variables de classes / locales / paramètres](#variables-de-classes--locales--paramètres)
	 4. [Constantes](#constantes)
 2. [Format](#format)
	 1. [Les crochets `{}`](#les-crochets-)
	 2. [Les appels de méthodes longs](#les-appels-de-méthodes-longs)
	 3. [Les espaces](#les-espaces)
	 4. [La documentation](#la-documentation)
	 5. [Les try-catch](#les-try-catch)

# Nommage

## Classes / Interfaces
Les noms de classes doivent être en CamelCase (upper camel case).
Les acronymes deviennent des mots normaux et ne sont pas en full maj.
 - Oui
	 - `MyClass`
	 - `Cell`
	 - `AVeryLongClassNameWithALotOfWords`
	 - `MyIhm`
- Non
	- `myClass`
	- `Someclass`
	- `AverylongClassNAMEwithAlotOFwords`
	- `MyIHM`
	- `a_class`

## Méthodes
Les noms de méthodes doivent être en camelCase (lower camel case).
- Oui
	- `getMe()`
	- `setThat()`
	- `dispatchThisThereAndDoThatPlz()`
- Non
	- `GetMe()`
	- `stopthis()`
	- `shut_up()`

## Variables de classes / locales / paramètres
Les noms de variables de classes et variables locales sont aussi en camelCase (lower camel case).
- Oui
	- `aVar`
	- `amount`
	- `myLongVarName`
- Non
	- `avar`
	- `my_long_var`
	- `MY_VAR`

## Constantes
Les constantes doivent être en majuscules et les différents mots séparés par un underscore (_).
Les constantes sont statiques et final.
- Oui
	- `public static final int MY_AWESOME_INT = 14;`
	- `private static final String ANOTHER_VAR = "hey";`
- Non
	- `public static final int myAwesomeInt = 14;`
	- `public static final int hello_there = 4654;`
	- `public int NOT_A_CONSTANT = 0;`
	- `public static int NOT_CONSTANT = 15;`
	- `public final int NOPE = 42;`

# Format

## Les crochets `{}`
Les crochets servent à délimiter des blocs de codes comme des méthodes/classes et doivent être mise à la fin de la même ligne.
- Oui
```java
public void myMethod() {
	// ...
}
```
- Non
```java
public void myMethod()
{
	// ...
}
```

Les crochets servent aussi à déclarer les arrays, dans ce cas là ils n'y a pas d'espaces.
- Oui
	- `int[] a = new int[]{1, 2, 3};`
	- `myMethod(new int[]{1, 2, 3});`
- Non
	- `int a[] = new int[] { 1, 2, 3 };`

## Les appels de méthodes longs
Lorsqu'il y a beaucoup de paramètres, à un certain point il faut les mettre sur plusieurs lignes et les grouper par affinités.
- Oui
```java
public void myMethod(final String componentName, final Supplier<Component> componentSupplier,
					 final Consumer<Void> onSuccess, final Runnable onFailure) {
	// ...
}

public void myVeryLongMethod(
	final String firstName, final String secondName, final String middleName,
	final int phoneNumberPart1, final int phoneNumberPart2,
	final String town, final String country
) {
	// ...
}
```

```java
aLongMethod(
	myArg1, myArg2, myName, myPhoneNumber,
	myCreditCardNumber, theThreeNumbersBehindIt,
	myAmazonPassword
);
```

- Non
```java
public void myVeryLongMethod(
	final String firstName, final String secondName, final String middleName, final int phoneNumberPart1, final int phoneNumberPart2, final String town, final String country) {
	// ...
}
```

## Les espaces
Mettez des espaces entre les opérateurs.
- Oui
	- `int a = 14;`
	- `if (a == 14) {}`
	- `if (a == 45 && (b != 45 || c >= 12)) {}`
	- `for (int a : numList) {}`
	- `for (int i = 0; i < 5; ++i) {}`
- Non
	- `int a=14;`
	- `int a= 14;`
	- `if (a!=5){}`
	- `if(a==1){}`
	- `if (a== 5&&(b!=12||c<= 80)) {}`

Mettez un espaces entre les paramètres, **après** la virgule.
- Oui
	- `public void myMethod(final int amount, final String name) {}`
	- `myMethod(15, "My name");`
- Non
	- `public void myMethod(int amount,String name) {}`
	- `public void myMethod(int amount ,String name) {}`
	- `myMethod(15,"a");`
	- `myMethod(15 ,"hey");`

## La documentation
La documentation utilise la syntaxe de la javadoc, est délimitée par `/**` et `*/` avec une astérisque par ligne.
Il y a une ligne vide entre la description et les paramètres.
Utilisez les tags spéciaux pour y mettre des choses (`{@code null}` pour mettre du code, `{@link Cell#getLocation()}` pour link d'autres méthodes/classes).
Les arguments sont alignés verticalement.

Une documentation typique:
```java
/**
 * My awesome description.
 */
public class MyClass {
	
	/**
	 * Description of the method, maybe multiple
	 * lines like that.
	 * 
	 * @param aVeryLongNameBecauseYolo - I am batman.
	 * @param name                     - The name.
	 * @return Modify the name with some fancy colors.
	 * @see #anotherMethod()
	 */
	public String myMethod(final int aVeryLongNameBecauseYolo, final String name) { /* */ }

	/**
	 * This time with a generic.
	 *
	 * @param <T> - Some generic stuff.
	 */
	public <T> T anotherMethod() { /* */ }
}
```

## Les try-catch
Pour utiliser l'API I/O de java (ou tout autre objet `Closeable`), il est préférable d'utiliser des try-catch-with-ressources.
- Oui
```java
try (FileInputStream fis = new FileInputStream(file)) {
	// ...
} catch (IOException e) {
	// ...
}
```
- Non
```java
FileInputStream fis = null;
try {
	fis = new FileInputStream(file);
	// ...
} catch (IOException e) {
	// ...
} finally {
	fis.close();
}
```
