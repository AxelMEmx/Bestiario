package com.bestiario.hp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Bestia::class], version = 1, exportSchema = false)
abstract class BestiaDatabase : RoomDatabase() {

    abstract fun bestiaDao(): BestiaDao

    companion object {
        @Volatile
        private var INSTANCE: BestiaDatabase? = null

        fun getDatabase(context: Context): BestiaDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BestiaDatabase::class.java,
                    "bestia_database"
                )
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            INSTANCE?.let { database ->
                                CoroutineScope(Dispatchers.IO).launch {
                                    poblarBaseDeDatos(database.bestiaDao())
                                }
                            }
                        }
                    })
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private suspend fun poblarBaseDeDatos(dao: BestiaDao) {
            dao.insertarTodas(listOf(
                Bestia(
                    nombre = "Acromántula",
                    nombreLatin = "Acromantula venomosa",
                    descripcion = "Araña gigante de ocho ojos capaz de hablar el lenguaje humano. Carnívora y altamente peligrosa. Vive en colonias matriarcales. Su veneno es muy valorado en pociones.",
                    nivelPeligro = 5,
                    habitat = "Selvas de Borneo y bosques densos",
                    habilidades = "Habla humana, veneno mortal, telaraña resistente, ocho ojos",
                    primeraAparicion = "La Cámara de los Secretos",
                    imagenUrl = "acromantula"
                ),
                Bestia(
                    nombre = "Ashwinder",
                    nombreLatin = "Serpens cinereus",
                    descripcion = "Serpiente delgada de color gris pálido con ojos rojo brillante, nacida de los rescoldos de fuegos mágicos sin atender. Solo vive una hora, durante la cual deposita huevos abrasadores.",
                    nivelPeligro = 3,
                    habitat = "Fuegos mágicos descuidados",
                    habilidades = "Cuerpo en llamas, huevos incendiarios",
                    primeraAparicion = "Animales Fantásticos y Dónde Encontrarlos",
                    imagenUrl = "ashwinder"
                ),
                Bestia(
                    nombre = "Augurey",
                    nombreLatin = "Augureyus irlandicus",
                    descripcion = "También conocido como Fénix Irlandés. Ave delgada de color verde y negro con un canto lúgubre. Antiguamente se creía que su llanto predecía la muerte.",
                    nivelPeligro = 2,
                    habitat = "Gran Bretaña e Irlanda, zonas lluviosas",
                    habilidades = "Predicción de lluvia, canto melancólico, vuelo silencioso",
                    primeraAparicion = "Animales Fantásticos y Dónde Encontrarlos",
                    imagenUrl = "augurey"
                ),
                Bestia(
                    nombre = "Basilisco",
                    nombreLatin = "Basiliscus letalis",
                    descripcion = "Serpiente gigante verde brillante capaz de matar con la mirada directa. Su veneno no tiene antídoto salvo las lágrimas de un fénix. Puede vivir más de 900 años.",
                    nivelPeligro = 5,
                    habitat = "Cámaras subterráneas húmedas",
                    habilidades = "Mirada mortal, veneno letal, longevidad extrema, control por Pársel",
                    primeraAparicion = "La Cámara de los Secretos",
                    imagenUrl = "basilisco"
                ),
                Bestia(
                    nombre = "Billywig",
                    nombreLatin = "Billywigus australis",
                    descripcion = "Insecto australiano de color zafiro con dos hélices en la cabeza que giran a gran velocidad. Su picadura causa mareo y levitación temporal.",
                    nivelPeligro = 3,
                    habitat = "Australia",
                    habilidades = "Vuelo helicoidal, picadura levitante, velocidad extrema",
                    primeraAparicion = "Animales Fantásticos y Dónde Encontrarlos",
                    imagenUrl = "billywig"
                ),
                Bestia(
                    nombre = "Bowtruckle",
                    nombreLatin = "Bowtrucklius arboris",
                    descripcion = "Pequeña criatura guardiana de árboles de madera con cualidades mágicas. Mide unos 20 cm. Difícil de detectar por su perfecto camuflaje vegetal.",
                    nivelPeligro = 1,
                    habitat = "Árboles de madera mágica (Reino Unido, Alemania, Escandinavia)",
                    habilidades = "Camuflaje perfecto, dedos afilados, abre cerraduras",
                    primeraAparicion = "Animales Fantásticos y Dónde Encontrarlos",
                    imagenUrl = "bowtruckle"
                ),
                Bestia(
                    nombre = "Bundimun",
                    nombreLatin = "Bundimunus fungus",
                    descripcion = "Criatura parecida a un hongo verde con ojos. Se alimenta de suciedad y puede pudrir los cimientos de cualquier construcción donde anide.",
                    nivelPeligro = 3,
                    habitat = "Casas sucias y abandonadas en todo el mundo",
                    habilidades = "Secreción ácida, multiplicación rápida, podredumbre estructural",
                    primeraAparicion = "Animales Fantásticos y Dónde Encontrarlos",
                    imagenUrl = "bundimun"
                ),
                Bestia(
                    nombre = "Centauro",
                    nombreLatin = "Centaurus sapiens",
                    descripcion = "Ser con torso humano y cuerpo de caballo. Habilidoso en adivinación, astronomía y arquería. Aunque clasificado como bestia, rechazan esa categoría por preferencia propia.",
                    nivelPeligro = 4,
                    habitat = "Bosques de Europa",
                    habilidades = "Adivinación, astronomía, arquería, sabiduría ancestral",
                    primeraAparicion = "La Piedra Filosofal",
                    imagenUrl = "centauro"
                ),
                Bestia(
                    nombre = "Quimera",
                    nombreLatin = "Chimaera tricapita",
                    descripcion = "Bestia griega rara y feroz con cabeza de león, cuerpo de cabra y cola de dragón. Sus huevos son clasificados como mercancía Clase A No Comerciable.",
                    nivelPeligro = 5,
                    habitat = "Grecia",
                    habilidades = "Aliento de fuego, fuerza brutal, agresividad extrema",
                    primeraAparicion = "Animales Fantásticos y Dónde Encontrarlos",
                    imagenUrl = "quimera"
                ),
                Bestia(
                    nombre = "Chizpurfle",
                    nombreLatin = "Chizpurflus parasitus",
                    descripcion = "Pequeño parásito de hasta 2 cm con grandes colmillos. Se siente atraído por la magia y suele infestar varitas, escobas y peines mágicos.",
                    nivelPeligro = 2,
                    habitat = "Objetos mágicos en todo el mundo",
                    habilidades = "Parasitismo mágico, masticación de núcleos de varita",
                    primeraAparicion = "Animales Fantásticos y Dónde Encontrarlos",
                    imagenUrl = "chizpurfle"
                ),
                Bestia(
                    nombre = "Clabbert",
                    nombreLatin = "Clabbertus arboreus",
                    descripcion = "Criatura arborícola parecida a un cruce entre mono y rana. Piel verde, dedos palmeados y una pústula en la frente que se enciende ante el peligro.",
                    nivelPeligro = 2,
                    habitat = "Árboles altos en EE.UU.",
                    habilidades = "Detección de peligro, pústula luminosa, salto entre ramas",
                    primeraAparicion = "Animales Fantásticos y Dónde Encontrarlos",
                    imagenUrl = "clabbert"
                ),
                Bestia(
                    nombre = "Crup",
                    nombreLatin = "Crupus bifurcatus",
                    descripcion = "Criatura idéntica a un Jack Russell Terrier pero con cola bifurcada. Extremadamente leal a los magos y ferozmente hostil con los muggles.",
                    nivelPeligro = 3,
                    habitat = "Sureste de Inglaterra",
                    habilidades = "Lealtad mágica, dieta omnívora (come hasta metal), agilidad",
                    primeraAparicion = "Animales Fantásticos y Dónde Encontrarlos",
                    imagenUrl = "crup"
                ),
                Bestia(
                    nombre = "Demiguise",
                    nombreLatin = "Demiguisus invisibilis",
                    descripcion = "Primate pacífico originario de Lejano Oriente. Puede volverse invisible y predecir el futuro inmediato, lo que hace su captura extremadamente difícil.",
                    nivelPeligro = 4,
                    habitat = "Lejano Oriente",
                    habilidades = "Invisibilidad a voluntad, visión precognitiva, pelaje plateado mágico",
                    primeraAparicion = "Animales Fantásticos y Dónde Encontrarlos",
                    imagenUrl = "demiguise"
                ),
                Bestia(
                    nombre = "Diricawl",
                    nombreLatin = "Diricawlus extinctus",
                    descripcion = "Ave regordeta no voladora con plumaje esponjoso. Los muggles la conocen como Dodo y la creen extinta, pero puede desaparecer y reaparecer a voluntad.",
                    nivelPeligro = 2,
                    habitat = "Mauricio",
                    habilidades = "Aparición y desaparición instantánea, escape mágico",
                    primeraAparicion = "Animales Fantásticos y Dónde Encontrarlos",
                    imagenUrl = "diricawl"
                ),
                Bestia(
                    nombre = "Doxy",
                    nombreLatin = "Doxyus venenosus",
                    descripcion = "Pequeño humanoide alado cubierto de pelo negro grueso, a menudo confundido con un hada. Tiene cuatro brazos y dos piernas. Su mordedura es venenosa.",
                    nivelPeligro = 3,
                    habitat = "Norte de Europa y América",
                    habilidades = "Vuelo, mordedura venenosa, reproducción rápida, cuatro brazos",
                    primeraAparicion = "La Orden del Fénix",
                    imagenUrl = "doxy"
                ),
                Bestia(
                    nombre = "Dragón Ojos de Ópalo Antípoda",
                    nombreLatin = "Draco opalophthalmus",
                    descripcion = "Dragón nativo de Nueva Zelanda. Considerado el más hermoso. Escamas iridiscentes y ojos sin pupila brillantes. Llamas rojo intenso.",
                    nivelPeligro = 4,
                    habitat = "Nueva Zelanda y Australia",
                    habilidades = "Vuelo, aliento de fuego rojo, escamas opalinas",
                    primeraAparicion = "Animales Fantásticos y Dónde Encontrarlos",
                    imagenUrl = "dragon_opaleye"
                ),
                Bestia(
                    nombre = "Bola de Fuego Chino",
                    nombreLatin = "Draco sinensis ignis",
                    descripcion = "Único dragón oriental. Rojo escarlata con flecos dorados. Hocico chato. Lanza bolas de fuego perfectamente esféricas por las fosas nasales.",
                    nivelPeligro = 4,
                    habitat = "China",
                    habilidades = "Bolas de fuego esféricas, vuelo, escamas escarlata",
                    primeraAparicion = "El Cáliz de Fuego",
                    imagenUrl = "dragon_chinese_fireball"
                ),
                Bestia(
                    nombre = "Verde Galés Común",
                    nombreLatin = "Draco viridis cambriensis",
                    descripcion = "Dragón verde de tamaño mediano. Se mezcla bien con la hierba. Su rugido es melodioso y advertencia para los humanos.",
                    nivelPeligro = 4,
                    habitat = "Gales (reserva oficial)",
                    habilidades = "Camuflaje en pastizales, aliento de fuego en chorros finos, agilidad",
                    primeraAparicion = "El Cáliz de Fuego",
                    imagenUrl = "dragon_welsh_green"
                ),
                Bestia(
                    nombre = "Negro de las Hébridas",
                    nombreLatin = "Draco hebridius niger",
                    descripcion = "Dragón nativo de Escocia. Aún más agresivo que el Verde Galés. Escamas negras ásperas, ojos morados brillantes y cresta dorsal afilada.",
                    nivelPeligro = 4,
                    habitat = "Hébridas (Escocia)",
                    habilidades = "Vuelo, aliento de fuego, cola afilada, agresividad extrema",
                    primeraAparicion = "Animales Fantásticos y Dónde Encontrarlos",
                    imagenUrl = "dragon_hebridean_black"
                ),
                Bestia(
                    nombre = "Colacuerno Húngaro",
                    nombreLatin = "Draco hungaricus cornuatus",
                    descripcion = "Considerado el más peligroso de todos los dragones. Escamas negras, ojos amarillos, cuernos de bronce y cola con púas. Su aliento alcanza 15 metros.",
                    nivelPeligro = 5,
                    habitat = "Hungría",
                    habilidades = "Aliento de fuego masivo, cola con púas, fuerza brutal, agresividad",
                    primeraAparicion = "El Cáliz de Fuego",
                    imagenUrl = "dragon_hungarian_horntail"
                ),
                Bestia(
                    nombre = "Ridgeback Noruego",
                    nombreLatin = "Draco norvegicus dorsalis",
                    descripcion = "Similar al Colacuerno Húngaro pero con cresta dorsal en lugar de cola con púas. Negro azulado. Inusualmente agresivo desde su nacimiento.",
                    nivelPeligro = 4,
                    habitat = "Noruega",
                    habilidades = "Aliento de fuego intenso, agresividad temprana, vuelo de altura",
                    primeraAparicion = "La Piedra Filosofal (Norbert)",
                    imagenUrl = "dragon_norwegian_ridgeback"
                ),
                Bestia(
                    nombre = "Vipertooth Peruano",
                    nombreLatin = "Draco peruvianus viperinus",
                    descripcion = "El más pequeño de los dragones conocidos. Escamas cobre lisas, cuernos cortos negros. Especialmente venenoso. Caza humanos con avidez.",
                    nivelPeligro = 5,
                    habitat = "Perú",
                    habilidades = "Veneno mortal, velocidad de vuelo, agresividad",
                    primeraAparicion = "Animales Fantásticos y Dónde Encontrarlos",
                    imagenUrl = "dragon_peruvian_vipertooth"
                ),
                Bestia(
                    nombre = "Longhorn Rumano",
                    nombreLatin = "Draco romanicus cornuatus",
                    descripcion = "Escamas verde oscuro y largos cuernos dorados con los que ensartan a sus presas antes de cocinarlas con su aliento. Sus cuernos son ingrediente raro.",
                    nivelPeligro = 4,
                    habitat = "Rumania (sede de la reserva mundial)",
                    habilidades = "Cuernos largos como lanzas, aliento de fuego, vuelo",
                    primeraAparicion = "Animales Fantásticos y Dónde Encontrarlos",
                    imagenUrl = "dragon_romanian_longhorn"
                ),
                Bestia(
                    nombre = "Hocicocorto Sueco",
                    nombreLatin = "Draco suecicus brevirostris",
                    descripcion = "Dragón azul plateado de hermosa apariencia. Su piel es muy buscada para guantes y escudos protectores. Su llama es azul brillante.",
                    nivelPeligro = 4,
                    habitat = "Suecia",
                    habilidades = "Llama azul de alta temperatura, vuelo, escamas resistentes",
                    primeraAparicion = "El Cáliz de Fuego",
                    imagenUrl = "dragon_swedish_short_snout"
                ),
                Bestia(
                    nombre = "Vientre de Hierro Ucraniano",
                    nombreLatin = "Draco ucranianus ferreus",
                    descripcion = "El dragón más grande del mundo, llega a pesar varias toneladas. Escamas metálicas y garras retráctiles. Custodia bóvedas profundas de Gringotts.",
                    nivelPeligro = 5,
                    habitat = "Ucrania",
                    habilidades = "Tamaño colosal, escamas metálicas, vuelo pese a su peso",
                    primeraAparicion = "Las Reliquias de la Muerte",
                    imagenUrl = "dragon_ukrainian_ironbelly"
                ),
                Bestia(
                    nombre = "Dugbog",
                    nombreLatin = "Dugbogus paludis",
                    descripcion = "Criatura pantanosa parecida a un trozo de madera muerta. Tiene dientes y garras afiladas. Devora pies de transeúntes incautos.",
                    nivelPeligro = 3,
                    habitat = "Pantanos de Europa y América",
                    habilidades = "Camuflaje como madera, dientes afilados, garras laceradoras",
                    primeraAparicion = "Animales Fantásticos y Dónde Encontrarlos",
                    imagenUrl = "dugbog"
                ),
                Bestia(
                    nombre = "Erkling",
                    nombreLatin = "Erklingus germanicus",
                    descripcion = "Criatura élfica con voz musical hipnotizante que atrae niños para devorarlos. Originaria de la Selva Negra. Tres veces el tamaño de un duende.",
                    nivelPeligro = 4,
                    habitat = "Bosques de Alemania",
                    habilidades = "Canto hipnótico, atracción de niños, agilidad",
                    primeraAparicion = "Animales Fantásticos y Dónde Encontrarlos",
                    imagenUrl = "erkling"
                ),
                Bestia(
                    nombre = "Erumpent",
                    nombreLatin = "Erumpentus africanus",
                    descripcion = "Bestia africana gigante parecida a un rinoceronte. Su cuerno contiene un fluido que hace explotar todo lo que perfora. Pacífica si no se le provoca.",
                    nivelPeligro = 4,
                    habitat = "África",
                    habilidades = "Cuerno explosivo, fuerza colosal, piel impenetrable",
                    primeraAparicion = "Las Reliquias de la Muerte",
                    imagenUrl = "erumpent"
                ),
                Bestia(
                    nombre = "Hada",
                    nombreLatin = "Fata vulgaris",
                    descripcion = "Pequeña criatura humanoide con alas de insecto y poca inteligencia. Vana y ruidosa. Suele decorarse a sí misma para llamar la atención.",
                    nivelPeligro = 2,
                    habitat = "Europa Occidental",
                    habilidades = "Vuelo, brillo natural, vanidad llamativa",
                    primeraAparicion = "El Cáliz de Fuego",
                    imagenUrl = "hada"
                ),
                Bestia(
                    nombre = "Cangrejo de Fuego",
                    nombreLatin = "Crabbus igneus",
                    descripcion = "Tortuga gigante con caparazón decorado con joyas. Cuando se ve amenazada, dispara fuego por la parte trasera de su caparazón.",
                    nivelPeligro = 3,
                    habitat = "Islas Fiji",
                    habilidades = "Fuego defensivo trasero, caparazón decorativo, protección mágica",
                    primeraAparicion = "Animales Fantásticos y Dónde Encontrarlos",
                    imagenUrl = "cangrejo_fuego"
                ),
                Bestia(
                    nombre = "Gusarajo",
                    nombreLatin = "Flobberwormus inertis",
                    descripcion = "Gusano marrón grueso de hasta 25 cm. Considerado increíblemente aburrido. Su único uso es como espesante en pociones. Vive comiendo lechuga.",
                    nivelPeligro = 1,
                    habitat = "Cunetas húmedas",
                    habilidades = "Mucosidad para pociones, dieta vegetariana, longevidad pasiva",
                    primeraAparicion = "El Prisionero de Azkaban",
                    imagenUrl = "gusarajo"
                ),
                Bestia(
                    nombre = "Fwooper",
                    nombreLatin = "Fwooperus africanus",
                    descripcion = "Ave africana de plumaje extremadamente brillante (naranja, rosa, verde lima o amarillo). Su canto vuelve loco a quien lo escucha; requiere encantamiento silenciador.",
                    nivelPeligro = 3,
                    habitat = "África",
                    habilidades = "Canto enloquecedor, plumaje vívido, vuelo elegante",
                    primeraAparicion = "Animales Fantásticos y Dónde Encontrarlos",
                    imagenUrl = "fwooper"
                ),
                Bestia(
                    nombre = "Ghoul",
                    nombreLatin = "Ghulus domesticus",
                    descripcion = "Criatura horripilante pero más bien tonta. Vive en áticos y graneros de casas mágicas. Pasa el día durmiendo y gimiendo. No suele atacar.",
                    nivelPeligro = 2,
                    habitat = "Áticos de casas mágicas",
                    habilidades = "Gemidos lúgubres, golpeo de tuberías, pereza monumental",
                    primeraAparicion = "Las Reliquias de la Muerte",
                    imagenUrl = "ghoul"
                ),
                Bestia(
                    nombre = "Glumbumble",
                    nombreLatin = "Glumbumbus melancholicus",
                    descripcion = "Insecto gris peludo del norte de Europa que produce melaza melancólica. Esta sustancia se usa para contrarrestar el efecto histérico de las hojas de Alihotsy.",
                    nivelPeligro = 3,
                    habitat = "Norte de Europa",
                    habilidades = "Producción de melaza melancólica, vuelo zumbante",
                    primeraAparicion = "Animales Fantásticos y Dónde Encontrarlos",
                    imagenUrl = "glumbumble"
                ),
                Bestia(
                    nombre = "Gnomo",
                    nombreLatin = "Gnomus horticulturalis",
                    descripcion = "Plaga de jardines mágicos. Pequeña criatura de cabeza grande y pies duros. Para deshacerse de ellos se les gira por las piernas y se les lanza.",
                    nivelPeligro = 2,
                    habitat = "Jardines de toda Europa",
                    habilidades = "Mordedura sorpresiva, excavación, multiplicación rápida",
                    primeraAparicion = "La Cámara de los Secretos",
                    imagenUrl = "gnomo"
                ),
                Bestia(
                    nombre = "Graphorn",
                    nombreLatin = "Graphornus montanus",
                    descripcion = "Criatura grande gris-púrpura con joroba prominente y dos cuernos dorados muy afilados. Trolls montañeses los montan, aunque pocos sobreviven al intento.",
                    nivelPeligro = 4,
                    habitat = "Montañas de Europa",
                    habilidades = "Cuernos dorados afilados, piel resistente a hechizos, fuerza brutal",
                    primeraAparicion = "Animales Fantásticos: Los Crímenes de Grindelwald",
                    imagenUrl = "graphorn"
                ),
                Bestia(
                    nombre = "Grifo",
                    nombreLatin = "Gryphus aquila",
                    descripcion = "Criatura con cabeza y patas delanteras de águila gigante, y cuerpo trasero de león. Empleada por magos para custodiar tesoros.",
                    nivelPeligro = 4,
                    habitat = "Grecia",
                    habilidades = "Vuelo poderoso, garras afiladas, lealtad como guardián",
                    primeraAparicion = "Animales Fantásticos y Dónde Encontrarlos",
                    imagenUrl = "grifo"
                ),
                Bestia(
                    nombre = "Grindylow",
                    nombreLatin = "Grindylowus aquaticus",
                    descripcion = "Demonio acuático verde pálido de cuernos pequeños y dedos largos. Habita en lagos. Aunque temible, sus dedos son frágiles y se rompen fácil.",
                    nivelPeligro = 2,
                    habitat = "Lagos de Gran Bretaña e Irlanda",
                    habilidades = "Sujeción con dedos largos, natación rápida, agresividad",
                    primeraAparicion = "El Prisionero de Azkaban",
                    imagenUrl = "grindylow"
                ),
                Bestia(
                    nombre = "Hidebehind",
                    nombreLatin = "Hidebehindus invisibilis",
                    descripcion = "Criatura nocturna del bosque norteamericano. Se oculta siempre detrás de algo, haciéndose virtualmente invisible. Caza a los muggles madereros.",
                    nivelPeligro = 4,
                    habitat = "Bosques de Norteamérica",
                    habilidades = "Invisibilidad por ocultación, sigilo total, caza nocturna",
                    primeraAparicion = "Animales Fantásticos (edición 2017)",
                    imagenUrl = "hidebehind"
                ),
                Bestia(
                    nombre = "Hipocampo",
                    nombreLatin = "Hippocampus marinus",
                    descripcion = "Tiene la cabeza y el cuerpo frontal de caballo, y trasero de gigantesco pez. Pone huevos translúcidos donde se ven los Tritonines nadando.",
                    nivelPeligro = 2,
                    habitat = "Grecia y costas mediterráneas",
                    habilidades = "Natación elegante, huevos translúcidos, comunicación con tritones",
                    primeraAparicion = "Animales Fantásticos y Dónde Encontrarlos",
                    imagenUrl = "hipocampo"
                ),
                Bestia(
                    nombre = "Hipogrifo",
                    nombreLatin = "Hippogryphus equinus",
                    descripcion = "Cabeza, alas y patas delanteras de águila gigante, cuerpo trasero de caballo. Requiere ser saludado con una reverencia antes de acercarse. Muy orgulloso.",
                    nivelPeligro = 3,
                    habitat = "Europa, ahora extendido por todo el mundo",
                    habilidades = "Vuelo veloz, garras afiladas, orgullo ceremonioso",
                    primeraAparicion = "El Prisionero de Azkaban",
                    imagenUrl = "hipogrifo"
                ),
                Bestia(
                    nombre = "Hodag",
                    nombreLatin = "Hodagus wisconsinensis",
                    descripcion = "Criatura norteamericana con cabeza de rana, cara de elefante, garras de oso y cola de dinosaurio. Bebe sangre de vacas y caza por las noches.",
                    nivelPeligro = 3,
                    habitat = "Wisconsin (EE.UU.)",
                    habilidades = "Caza nocturna, mordida poderosa, vampirismo bovino",
                    primeraAparicion = "Animales Fantásticos (edición 2017)",
                    imagenUrl = "hodag"
                ),
                Bestia(
                    nombre = "Horklump",
                    nombreLatin = "Horklumpus rosaceus",
                    descripcion = "Parece un hongo rosa carnoso cubierto de cerdas negras escasas. Se reproduce velozmente. Es alimento favorito de los Gnomos.",
                    nivelPeligro = 1,
                    habitat = "Norte de Europa",
                    habilidades = "Reproducción veloz, raíces subterráneas, dieta de gusanos",
                    primeraAparicion = "Animales Fantásticos y Dónde Encontrarlos",
                    imagenUrl = "horklump"
                ),
                Bestia(
                    nombre = "Serpiente Cornuda",
                    nombreLatin = "Serpens cornuatus americanus",
                    descripcion = "Serpiente con joya en la frente. Es la mascota de la casa Pukwudgie en Ilvermorny. Sus cuernos detectan brujas y magos honestos.",
                    nivelPeligro = 4,
                    habitat = "Norteamérica",
                    habilidades = "Detección de honestidad, joya frontal mágica, sabiduría",
                    primeraAparicion = "Animales Fantásticos (edición 2017)",
                    imagenUrl = "horned_serpent"
                ),
                Bestia(
                    nombre = "Diablillo",
                    nombreLatin = "Imp britannicus",
                    descripcion = "Criatura pequeña de 15-20 cm con sentido del humor cruel. Empuja a sus víctimas y se ríe. Más limitado intelectualmente que un duende.",
                    nivelPeligro = 2,
                    habitat = "Gran Bretaña e Irlanda",
                    habilidades = "Bromas crueles, sigilo, salto",
                    primeraAparicion = "Animales Fantásticos y Dónde Encontrarlos",
                    imagenUrl = "diablillo"
                ),
                Bestia(
                    nombre = "Jarvey",
                    nombreLatin = "Jarveyus loquax",
                    descripcion = "Parece un hurón gigante. Capaz de habla limitada, pero solo dice frases cortas, generalmente groseras o insultantes. Caza Gnomos.",
                    nivelPeligro = 3,
                    habitat = "Gran Bretaña, Irlanda y Norteamérica",
                    habilidades = "Habla grosera, caza de gnomos, excavación",
                    primeraAparicion = "Animales Fantásticos y Dónde Encontrarlos",
                    imagenUrl = "jarvey"
                ),
                Bestia(
                    nombre = "Jobberknoll",
                    nombreLatin = "Jobberknollus memoriae",
                    descripcion = "Pájaro pequeño azul moteado que no emite sonido alguno en su vida. Al morir, expulsa un largo grito con todos los sonidos que escuchó al revés.",
                    nivelPeligro = 2,
                    habitat = "Europa y América del Norte",
                    habilidades = "Memoria sonora perfecta, mutismo vital, plumas para Pociones de la Verdad",
                    primeraAparicion = "Animales Fantásticos y Dónde Encontrarlos",
                    imagenUrl = "jobberknoll"
                ),
                Bestia(
                    nombre = "Kappa",
                    nombreLatin = "Kappa japonicus",
                    descripcion = "Demonio acuático japonés con apariencia de mono escamoso con membranas en los dedos. Tiene un hueco en la cabeza lleno de agua que es su debilidad.",
                    nivelPeligro = 4,
                    habitat = "Estanques y ríos poco profundos de Japón",
                    habilidades = "Estrangulamiento, natación, debilidad por reverencia (vacía su cuenco)",
                    primeraAparicion = "El Prisionero de Azkaban",
                    imagenUrl = "kappa"
                ),
                Bestia(
                    nombre = "Kelpie",
                    nombreLatin = "Kelpius aquaticus",
                    descripcion = "Demonio acuático de Gran Bretaña e Irlanda. Puede tomar muchas formas, generalmente la de un caballo con juncos por crin. Ahoga a los jinetes incautos.",
                    nivelPeligro = 4,
                    habitat = "Lagos de Gran Bretaña e Irlanda",
                    habilidades = "Metamorfosis, ahogamiento, atracción de jinetes",
                    primeraAparicion = "Animales Fantásticos y Dónde Encontrarlos",
                    imagenUrl = "kelpie"
                ),
                Bestia(
                    nombre = "Knarl",
                    nombreLatin = "Knarlus hispidus",
                    descripcion = "Indistinguible de un erizo común, pero rechaza la comida ofrecida en jardines, pensando que es una trampa, y destruye las plantas en venganza.",
                    nivelPeligro = 3,
                    habitat = "Europa del Norte y América",
                    habilidades = "Detección de engaño, destrucción de jardines, púas defensivas",
                    primeraAparicion = "Animales Fantásticos y Dónde Encontrarlos",
                    imagenUrl = "knarl"
                ),
                Bestia(
                    nombre = "Kneazle",
                    nombreLatin = "Kneazlus felinus",
                    descripcion = "Parecido a un gato pero con orejas grandes y cola como de león. Inteligente, independiente y a veces agresivo. Detecta personajes sospechosos o no confiables.",
                    nivelPeligro = 3,
                    habitat = "Gran Bretaña, cría doméstica mundial",
                    habilidades = "Detección de personas no confiables, lealtad, orientación",
                    primeraAparicion = "El Prisionero de Azkaban (Crookshanks es mestizo)",
                    imagenUrl = "kneazle"
                ),
                Bestia(
                    nombre = "Leprechaun",
                    nombreLatin = "Leprechaunus irlandicus",
                    descripcion = "Pequeño humanoide irlandés de unos 15 cm, color verde. Crea oro que desaparece después de unas horas. Más inteligente que el hada o el duende.",
                    nivelPeligro = 3,
                    habitat = "Irlanda",
                    habilidades = "Creación de oro temporal, vuelo, picardía",
                    primeraAparicion = "El Cáliz de Fuego",
                    imagenUrl = "leprechaun"
                ),
                Bestia(
                    nombre = "Lethifold",
                    nombreLatin = "Lethifoldus mortiferus",
                    descripcion = "Capa negra que se desliza por el suelo durante la noche. Asfixia a sus víctimas mientras duermen y luego las digiere. Solo se repele con Patronus.",
                    nivelPeligro = 5,
                    habitat = "Climas tropicales",
                    habilidades = "Asfixia silenciosa, digestión total, sigilo absoluto",
                    primeraAparicion = "Animales Fantásticos y Dónde Encontrarlos",
                    imagenUrl = "lethifold"
                ),
                Bestia(
                    nombre = "Lobalug",
                    nombreLatin = "Lobalugus marinus",
                    descripcion = "Habitante del Mar del Norte. Mide unos 25 cm. Posee un saco venenoso usado por los Tritones como arma. Los magos usan su veneno en pociones.",
                    nivelPeligro = 2,
                    habitat = "Fondo del Mar del Norte",
                    habilidades = "Veneno paralizante, natación lenta, saco defensivo",
                    primeraAparicion = "Animales Fantásticos y Dónde Encontrarlos",
                    imagenUrl = "lobalug"
                ),
                Bestia(
                    nombre = "Malaclaw Manchado",
                    nombreLatin = "Malaclawus infortunatus",
                    descripcion = "Criatura parecida a una langosta de piedra. Su mordedura provoca mala suerte durante una semana. Evite jugar a la lotería tras ser mordido.",
                    nivelPeligro = 3,
                    habitat = "Costas rocosas de Europa",
                    habilidades = "Mordedura que causa mala suerte, pinzas duras, camuflaje rocoso",
                    primeraAparicion = "Animales Fantásticos y Dónde Encontrarlos",
                    imagenUrl = "malaclaw"
                ),
                Bestia(
                    nombre = "Mantícora",
                    nombreLatin = "Manticora devorans",
                    descripcion = "Criatura griega con cabeza humana, cuerpo de león y cola de escorpión. Su aguijón provoca muerte instantánea. Canta mientras devora.",
                    nivelPeligro = 5,
                    habitat = "Grecia",
                    habilidades = "Aguijón mortal, fuerza sobrehumana, canto hipnótico mientras mata",
                    primeraAparicion = "El Prisionero de Azkaban",
                    imagenUrl = "manticora"
                ),
                Bestia(
                    nombre = "Tritón / Sirena",
                    nombreLatin = "Merpersonus aquaticus",
                    descripcion = "Habitantes acuáticos inteligentes. Existen distintas razas: Sirenas griegas (hermosas), Selkies escoceses y Tritones del Lago Negro (feos verdes con dientes amarillos).",
                    nivelPeligro = 2,
                    habitat = "Mares y lagos profundos del mundo",
                    habilidades = "Comunicación submarina, sociedad organizada, herramientas de caza",
                    primeraAparicion = "El Cáliz de Fuego",
                    imagenUrl = "tritones"
                ),
                Bestia(
                    nombre = "Moke",
                    nombreLatin = "Mokus changeus",
                    descripcion = "Lagartija de plata-verde de hasta 25 cm. Puede encogerse a voluntad, por lo que nunca ha sido vista por muggles. Su piel se usa para monederos.",
                    nivelPeligro = 3,
                    habitat = "Gran Bretaña e Irlanda",
                    habilidades = "Encogimiento a voluntad, camuflaje, piel mágica protectora",
                    primeraAparicion = "Animales Fantásticos y Dónde Encontrarlos",
                    imagenUrl = "moke"
                ),
                Bestia(
                    nombre = "Mooncalf",
                    nombreLatin = "Moncalfus lunaris",
                    descripcion = "Criatura tímida que sale de su madriguera solo durante la luna llena. Realiza danzas elaboradas en las patas traseras. Estiércol valioso para plantas.",
                    nivelPeligro = 2,
                    habitat = "Madrigueras de todo el mundo",
                    habilidades = "Danza lunar, ojos enormes saltones, ternura",
                    primeraAparicion = "Animales Fantásticos: Los Crímenes de Grindelwald",
                    imagenUrl = "mooncalf"
                ),
                Bestia(
                    nombre = "Murtlap",
                    nombreLatin = "Murtlapus marinus",
                    descripcion = "Criatura marina parecida a una rata con un crecimiento como de anémona en la espalda. El crecimiento se usa para curar cortes y resistir hechizos.",
                    nivelPeligro = 3,
                    habitat = "Costas de Gran Bretaña",
                    habilidades = "Mordedura ácida, crecimiento curativo, anidamiento en arena",
                    primeraAparicion = "La Orden del Fénix",
                    imagenUrl = "murtlap"
                ),
                Bestia(
                    nombre = "Niffler",
                    nombreLatin = "Nifflerus aurumvorus",
                    descripcion = "Pequeño mamífero negro con hocico largo y bolsa abdominal infinita. Irresistiblemente atraído por todo objeto brillante. Excelente para encontrar tesoros.",
                    nivelPeligro = 1,
                    habitat = "Madrigueras subterráneas",
                    habilidades = "Detección de objetos brillantes, bolsa dimensional, excavación rápida",
                    primeraAparicion = "Animales Fantásticos y Dónde Encontrarlos",
                    imagenUrl = "niffler"
                ),
                Bestia(
                    nombre = "Nogtail",
                    nombreLatin = "Nogtailus porcinus",
                    descripcion = "Demonio raro parecido a un lechón con patas largas, cola corta y ojos negros entrecerrados. Su crianza junto a cerdos arruina las cosechas.",
                    nivelPeligro = 3,
                    habitat = "Granjas rurales europeas",
                    habilidades = "Maldición de cosechas, sigilo, debe ser expulsado por perro albino",
                    primeraAparicion = "Animales Fantásticos y Dónde Encontrarlos",
                    imagenUrl = "nogtail"
                ),
                Bestia(
                    nombre = "Nundu",
                    nombreLatin = "Nundus africanus",
                    descripcion = "Leopardo africano gigante. Considerada la criatura más peligrosa del mundo. Su aliento expele enfermedades que aniquilan pueblos enteros.",
                    nivelPeligro = 5,
                    habitat = "África Oriental",
                    habilidades = "Aliento pestilente mortal, sigilo a pesar de su tamaño, fuerza brutal",
                    primeraAparicion = "Animales Fantásticos y Dónde Encontrarlos",
                    imagenUrl = "nundu"
                ),
                Bestia(
                    nombre = "Occamy",
                    nombreLatin = "Occamy plumiserpens",
                    descripcion = "Criatura emplumada de dos patas con cuerpo de serpiente. Carnívora y muy agresiva al defender huevos. Sus cáscaras de huevo son de plata pura.",
                    nivelPeligro = 4,
                    habitat = "Lejano Oriente e India",
                    habilidades = "Coranaptismo (cambia su tamaño al espacio), agresividad protectora, huevos de plata",
                    primeraAparicion = "Animales Fantásticos y Dónde Encontrarlos",
                    imagenUrl = "occamy"
                ),
                Bestia(
                    nombre = "Fénix",
                    nombreLatin = "Phoenix immortalis",
                    descripcion = "Ave mágica de plumaje rojo y dorado que renace de sus cenizas. Sus lágrimas tienen propiedades curativas. Puede cargar pesos enormes.",
                    nivelPeligro = 4,
                    habitat = "Montañas remotas de Egipto, India y China",
                    habilidades = "Renacimiento, llanto curativo, teletransporte por fuego, carga de peso",
                    primeraAparicion = "La Cámara de los Secretos (Fawkes)",
                    imagenUrl = "fenix"
                ),
                Bestia(
                    nombre = "Duendecillo de Cornualles",
                    nombreLatin = "Pixius cornubiensis",
                    descripcion = "Pequeña criatura azul eléctrico de hasta 20 cm. Excesivamente traviesa, le encanta hacer trucos crueles a magos y muggles por igual.",
                    nivelPeligro = 3,
                    habitat = "Cornualles, Inglaterra",
                    habilidades = "Vuelo, fuerza para levantar humanos, voz aguda, travesuras",
                    primeraAparicion = "La Cámara de los Secretos",
                    imagenUrl = "duendecillo"
                ),
                Bestia(
                    nombre = "Plimpy",
                    nombreLatin = "Plimpius globularis",
                    descripcion = "Pez esférico con dos largas patas. Inofensivo. Su único pasatiempo es atar las piernas de Tritones bajo el agua. Los Tritones les hacen nudos.",
                    nivelPeligro = 2,
                    habitat = "Lagos profundos europeos",
                    habilidades = "Natación, nudos lentos, dieta de caracoles",
                    primeraAparicion = "Animales Fantásticos y Dónde Encontrarlos",
                    imagenUrl = "plimpy"
                ),
                Bestia(
                    nombre = "Pogrebin",
                    nombreLatin = "Pogrebinus desesperatus",
                    descripcion = "Demonio peludo ruso de unos 30 cm. Sigue a los humanos, causándoles depresión y pensamientos inútiles hasta que se rinden.",
                    nivelPeligro = 3,
                    habitat = "Rusia",
                    habilidades = "Inducción de desesperación, persecución silenciosa, camuflaje como roca",
                    primeraAparicion = "Animales Fantásticos y Dónde Encontrarlos",
                    imagenUrl = "pogrebin"
                ),
                Bestia(
                    nombre = "Porlock",
                    nombreLatin = "Porlockus equinus",
                    descripcion = "Guardián de caballos. Pequeña criatura peluda de dos patas con cabeza grande y nariz desproporcionada. Se aleja de humanos pero ama los caballos.",
                    nivelPeligro = 2,
                    habitat = "Inglaterra meridional e Irlanda",
                    habilidades = "Lealtad equina, vigilancia nocturna, dieta de hierba",
                    primeraAparicion = "Animales Fantásticos y Dónde Encontrarlos",
                    imagenUrl = "porlock"
                ),
                Bestia(
                    nombre = "Puffskein",
                    nombreLatin = "Puffskeinus globosus",
                    descripcion = "Esfera peluda de color crema. Mascota mágica popular y dócil. Emite un zumbido bajo cuando es feliz. Su lengua extralarga limpia las narices durmientes.",
                    nivelPeligro = 2,
                    habitat = "Mundialmente como mascota",
                    habilidades = "Docilidad total, dieta omnívora, lengua larga, rebote",
                    primeraAparicion = "Animales Fantásticos y Dónde Encontrarlos",
                    imagenUrl = "puffskein"
                ),
                Bestia(
                    nombre = "Quintaped",
                    nombreLatin = "Quintapedus pentamembris",
                    descripcion = "Criatura peluda de cinco patas, también llamada McBoon Peludo. Carnívora con preferencia por humanos. Endémica de la Isla Drear.",
                    nivelPeligro = 5,
                    habitat = "Isla Drear (Escocia)",
                    habilidades = "Cinco patas con pezuñas, voracidad humana, agresividad extrema",
                    primeraAparicion = "Animales Fantásticos y Dónde Encontrarlos",
                    imagenUrl = "quintaped"
                ),
                Bestia(
                    nombre = "Ramora",
                    nombreLatin = "Ramora marinus",
                    descripcion = "Pez plateado que vive en el Océano Índico. Tiene poderes mágicos poderosos: puede anclar barcos y es guardiana de los navegantes. Protegida internacionalmente.",
                    nivelPeligro = 2,
                    habitat = "Océano Índico",
                    habilidades = "Anclaje mágico de barcos, protección de marinos, telepatía marítima",
                    primeraAparicion = "Animales Fantásticos y Dónde Encontrarlos",
                    imagenUrl = "ramora"
                ),
                Bestia(
                    nombre = "Red Cap",
                    nombreLatin = "Redcapus belicosus",
                    descripcion = "Criatura enana similar a un duende que vive en campos de batalla. Golpea con palos a viajeros perdidos para refrescar el rojo de su sombrero con sangre.",
                    nivelPeligro = 3,
                    habitat = "Campos de batalla antiguos del norte de Europa",
                    habilidades = "Golpes con palos, sigilo nocturno, atracción por la violencia",
                    primeraAparicion = "El Cáliz de Fuego",
                    imagenUrl = "redcap"
                ),
                Bestia(
                    nombre = "Re'em",
                    nombreLatin = "Reemus colossus",
                    descripcion = "Buey gigante extremadamente raro encontrado en Norteamérica y el Lejano Oriente. Beber su sangre otorga fuerza inmensa por horas.",
                    nivelPeligro = 4,
                    habitat = "Áreas remotas de Norteamérica y Lejano Oriente",
                    habilidades = "Tamaño descomunal, sangre potenciadora, cuernos gigantes",
                    primeraAparicion = "Animales Fantásticos y Dónde Encontrarlos",
                    imagenUrl = "reem"
                ),
                Bestia(
                    nombre = "Runespoor",
                    nombreLatin = "Runespoorus tricapitatus",
                    descripcion = "Serpiente de tres cabezas, cada una con una función: planificadora, soñadora y crítica. Las tres se pelean entre sí. Famosa entre Magos Tenebrosos.",
                    nivelPeligro = 4,
                    habitat = "Burkina Faso",
                    habilidades = "Tres cabezas con personalidades, veneno, puesta de huevos por la boca",
                    primeraAparicion = "Animales Fantásticos y Dónde Encontrarlos",
                    imagenUrl = "runespoor"
                ),
                Bestia(
                    nombre = "Salamandra",
                    nombreLatin = "Salamandra ignifera",
                    descripcion = "Lagarto que nace del fuego y vive en las llamas. Mientras se le suministre fuego, puede sobrevivir indefinidamente. Su sangre tiene poderes curativos.",
                    nivelPeligro = 3,
                    habitat = "Fuegos en todo el mundo",
                    habilidades = "Vida en el fuego, sangre curativa, resistencia al calor",
                    primeraAparicion = "Animales Fantásticos: Los Crímenes de Grindelwald",
                    imagenUrl = "salamandra"
                ),
                Bestia(
                    nombre = "Serpiente Marina",
                    nombreLatin = "Serpens marinus",
                    descripcion = "Encontradas en océanos de todo el mundo. Tienen cabeza de caballo, largo cuerpo serpentino. Hasta 30 metros. Pese a su apariencia, no son agresivas.",
                    nivelPeligro = 3,
                    habitat = "Océanos Atlántico, Pacífico y Mediterráneo",
                    habilidades = "Tamaño gigantesco, natación rápida, longevidad",
                    primeraAparicion = "Animales Fantásticos y Dónde Encontrarlos",
                    imagenUrl = "serpiente_marina"
                ),
                Bestia(
                    nombre = "Shrake",
                    nombreLatin = "Shrakus spinosus",
                    descripcion = "Pez espinoso que habita aguas profundas del Atlántico. Daña redes de pesca muggle por venganza tras un incidente con magos en 1894.",
                    nivelPeligro = 3,
                    habitat = "Océano Atlántico",
                    habilidades = "Espinas afiladas, destrucción de redes, hostilidad a muggles",
                    primeraAparicion = "Animales Fantásticos y Dónde Encontrarlos",
                    imagenUrl = "shrake"
                ),
                Bestia(
                    nombre = "Snallygaster",
                    nombreLatin = "Snallygasterus alatus",
                    descripcion = "Criatura mitad reptil mitad ave de Norteamérica. Difícil de ocultar. Sus dientes son afilados como navajas. Ha causado múltiples violaciones del Estatuto.",
                    nivelPeligro = 4,
                    habitat = "Maryland y costa este de EE.UU.",
                    habilidades = "Vuelo veloz, dientes navaja, agresividad territorial",
                    primeraAparicion = "Animales Fantásticos (edición 2017)",
                    imagenUrl = "snallygaster"
                ),
                Bestia(
                    nombre = "Snidget",
                    nombreLatin = "Snidgetus dorado",
                    descripcion = "Ave dorada redonda extremadamente rara. Vuela rápido y cambia de dirección a 90 grados. Originó la Snitch Dorada del Quidditch. Hoy especie protegida.",
                    nivelPeligro = 4,
                    habitat = "Norte de Europa",
                    habilidades = "Vuelo errático ultra rápido, plumaje dorado, ojos rojos",
                    primeraAparicion = "Quidditch a Través de los Tiempos",
                    imagenUrl = "snidget"
                ),
                Bestia(
                    nombre = "Esfinge",
                    nombreLatin = "Sphinx aenigmatica",
                    descripcion = "Cuerpo de león con cabeza humana. Usada por magos como guardiana. Adora los acertijos y atacará a quien no resuelva sus enigmas.",
                    nivelPeligro = 4,
                    habitat = "Egipto",
                    habilidades = "Acertijos elaborados, fuerza leonina, inteligencia, lealtad como guardián",
                    primeraAparicion = "El Cáliz de Fuego",
                    imagenUrl = "esfinge"
                ),
                Bestia(
                    nombre = "Streeler",
                    nombreLatin = "Streelerus colorambius",
                    descripcion = "Caracol gigante que cambia de color cada hora. Deja un rastro venenoso que mata la vegetación. Mascota popular por su belleza visual.",
                    nivelPeligro = 3,
                    habitat = "África",
                    habilidades = "Cambio de color, veneno vegetal, caparazón tornasolado",
                    primeraAparicion = "Animales Fantásticos y Dónde Encontrarlos",
                    imagenUrl = "streeler"
                ),
                Bestia(
                    nombre = "Tebo",
                    nombreLatin = "Teboius invisibilis",
                    descripcion = "Jabalí gris-ceniza africano con habilidad de volverse invisible. Animal extremadamente peligroso. Su piel es valorada como protección mágica.",
                    nivelPeligro = 4,
                    habitat = "Congo y Zaire",
                    habilidades = "Invisibilidad, embestida, piel protectora mágica",
                    primeraAparicion = "Animales Fantásticos y Dónde Encontrarlos",
                    imagenUrl = "tebo"
                ),
                Bestia(
                    nombre = "Thestral",
                    nombreLatin = "Thestralus invisibilis",
                    descripcion = "Caballo alado esquelético, visible solo para quienes han presenciado la muerte. Manso pese a su apariencia. Excelente sentido de la orientación.",
                    nivelPeligro = 2,
                    habitat = "Bosques de Gran Bretaña, Francia e Irlanda",
                    habilidades = "Vuelo silencioso, invisibilidad selectiva, navegación perfecta",
                    primeraAparicion = "La Orden del Fénix",
                    imagenUrl = "thestral"
                ),
                Bestia(
                    nombre = "Thunderbird",
                    nombreLatin = "Thunderbirdus tempestas",
                    descripcion = "Ave gigante norteamericana que crea tormentas al volar. Tiene tres pares de alas iridiscentes y puede percibir el peligro. Mascota de la casa Thunderbird en Ilvermorny.",
                    nivelPeligro = 4,
                    habitat = "Arizona (EE.UU.)",
                    habilidades = "Creación de tormentas, vuelo de larga distancia, percepción del peligro",
                    primeraAparicion = "Animales Fantásticos y Dónde Encontrarlos (Frank)",
                    imagenUrl = "thunderbird"
                ),
                Bestia(
                    nombre = "Troll",
                    nombreLatin = "Trollus brutus",
                    descripcion = "Criatura humanoide gigante de hasta 4 metros y una tonelada de peso. Estúpido, brutal y peligroso. Existen variedades: de Montaña, de Bosque y de Río.",
                    nivelPeligro = 4,
                    habitat = "Escandinavia, Gran Bretaña e Irlanda",
                    habilidades = "Fuerza brutal, piel impenetrable, garrote letal, regeneración",
                    primeraAparicion = "La Piedra Filosofal",
                    imagenUrl = "troll"
                ),
                Bestia(
                    nombre = "Unicornio",
                    nombreLatin = "Unicornus purissimus",
                    descripcion = "Caballo blanco puro de cuerno único. Símbolo absoluto de pureza. Su sangre mantiene vivo a quien la bebe, pero con una vida maldita. Más confiado con mujeres.",
                    nivelPeligro = 4,
                    habitat = "Bosques de Europa",
                    habilidades = "Sangre prolongadora de vida, cuerno mágico, crines y pelo para varitas",
                    primeraAparicion = "La Piedra Filosofal",
                    imagenUrl = "unicornio"
                ),
                Bestia(
                    nombre = "Wampus",
                    nombreLatin = "Wampus felinus",
                    descripcion = "Gran felino con seis patas, similar a una pantera. Originario de Apalaches. Hipnotiza con la mirada y lee mentes. Mascota de la casa Wampus en Ilvermorny.",
                    nivelPeligro = 5,
                    habitat = "Apalaches (EE.UU.)",
                    habilidades = "Legilimencia, hipnosis con la mirada, seis patas veloces, garras retráctiles",
                    primeraAparicion = "Animales Fantásticos (edición 2017)",
                    imagenUrl = "wampus"
                ),
                Bestia(
                    nombre = "Hombre Lobo",
                    nombreLatin = "Lupus humanus mutans",
                    descripcion = "Humano mordido por otro hombre lobo. Se transforma en bestia lupina durante la luna llena, perdiendo todo control. La poción Matalobos mitiga los síntomas.",
                    nivelPeligro = 5,
                    habitat = "Mundialmente, en su forma humana",
                    habilidades = "Transformación lunar, fuerza salvaje, sentidos agudizados, agresividad sin control",
                    primeraAparicion = "El Prisionero de Azkaban (Lupin)",
                    imagenUrl = "hombrelobo"
                ),
                Bestia(
                    nombre = "Caballo Alado",
                    nombreLatin = "Equus alatus",
                    descripcion = "Existen varias razas: Abraxan (gigantes palominos), Aethonan (castaños), Granian (gris veloz) y el Thestral (alimentado por carne). Volar es su característica común.",
                    nivelPeligro = 3,
                    habitat = "Mundialmente, distintas razas",
                    habilidades = "Vuelo, fuerza equina, distintas habilidades según raza",
                    primeraAparicion = "El Cáliz de Fuego (carruaje de Beauxbatons)",
                    imagenUrl = "caballo_alado"
                ),
                Bestia(
                    nombre = "Yeti",
                    nombreLatin = "Yetus tibetanus",
                    descripcion = "Bigfoot tibetano. Primo lejano del troll. Mide hasta 4.5 metros. Pelaje blanco-puro. Come todo lo que se cruza en su camino. Teme al fuego.",
                    nivelPeligro = 4,
                    habitat = "Tibet",
                    habilidades = "Fuerza brutal, resistencia al frío extremo, voracidad",
                    primeraAparicion = "Animales Fantásticos y Dónde Encontrarlos",
                    imagenUrl = "yeti"
                ),
                Bestia(
                    nombre = "Zouwu",
                    nombreLatin = "Zouwu sinensis",
                    descripcion = "Bestia mítica china de cinco patas (no es un error), tamaño de elefante, con cola colorida y cara felina. Puede recorrer 1.000 km al día. A pesar de su apariencia, juguetón.",
                    nivelPeligro = 4,
                    habitat = "China",
                    habilidades = "Velocidad sobrenatural, fuerza colosal, cola colorida vistosa",
                    primeraAparicion = "Animales Fantásticos: Los Crímenes de Grindelwald",
                    imagenUrl = "zouwu"
                )
            ))
        }
    }
}
