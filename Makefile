### Variables ###
JC = javac -d build -sourcepath src ${SRC}/*.java -encoding UTF-8 -implicit:none
JAVA = java
JAR = jar
JARFLAGS = -cvef

# CHEMINS RELATIFS
SRC = src/fr/iutfbleau/SAEDev32_2023FI2/GroupeMAN
BUILD = build/fr/iutfbleau/SAEDev32_2023FI2/GroupeMAN
CHEM = fr/iutfbleau/SAEDev32_2023FI2/GroupeMAN
DOC = doc/fr/iutfbleau/SAEDev32_2023FI2/GroupeMAN

### ETAPES/REGLES DE COMPILATION ###

#DEV32_2023
${BUILD}/Main.class : ${SRC}/Main.java ${BUILD}/Tableur.class
	${JC} ${SRC}/Main.java

${BUILD}/Cell.class ${BUILD}/CellTab.class ${BUILD}/Arbre.class: ${SRC}/Cell.java ${SRC}/Arbre.java ${SRC}/CellTab.java ${BUILD}/Noeud.class ${BUILD}/Pile.class
	${JC} ${SRC}/Cell.java ${SRC}/Arbre.java ${SRC}/CellTab.java

${BUILD}/Tableur.class : ${SRC}/Tableur.java ${BUILD}/Cell.class ${BUILD}/CellTab.class
	${JC} ${SRC}/Tableur.java

${BUILD}/CelluleTableur.class : ${SRC}/CelluleTableur.java ${BUILD}/Cell.class ${BUILD}/CellTab.class
	${JC} ${SRC}/CelluleTableur.java

${BUILD}/Noeud.class : ${SRC}/Noeud.java
	${JC} ${SRC}/Noeud.java

${BUILD}/Pile.class : ${SRC}/Pile.java 
	${JC} ${SRC}/Pile.java


### Créer le JAR exécutable DEV32_2023.jar en utilisant make ###
DEV32_2023.jar: ${BUILD}/Main.class ${BUILD}/Cell.class ${BUILD}/Tableur.class ${BUILD}/CelluleTableur.class ${BUILD}/Arbre.class ${BUILD}/CellTab.class ${BUILD}/Noeud.class ${BUILD}/Pile.class
	${JAR} ${JARFLAGS} fr.iutfbleau.SAEDev32_2023FI2.GroupeMAN.Main DEV32_2023.jar -C build ${CHEM} .


all: DEV32_2023.jar

### Documentation ###

doc :
	mkdir doc
	javadoc -d doc ./${SRC}/*.java

### RULES ###
run : 
	${JAVA} -jar DEV32_2023.jar

clean :
	rm -rf ${BUILD}/*.class
	rm -f DEV32_2023.jar
	rm -rf doc

###BUTS FACTICE###
.PHONY : run clean doc all