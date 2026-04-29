package src;
import java.util.*;

public class Mazo {

    private List<Carta> mazoGeneral = new ArrayList<>();

    public Mazo() {
        this.mazoGeneral.add(new Monstruo("Mago Oscuro", "El mago definitivo en términos de ataque y defensa.", (byte) 7, (short) 2500, (short) 2100));
        this.mazoGeneral.add(new Monstruo("Dragón Blanco de Ojos Azules", "Una poderosa máquina de destrucción, casi invencible.", (byte) 8, (short) 3000, (short) 2500));
        this.mazoGeneral.add(new Monstruo("Cráneo Convocado", "Un demonio con poderes oscuros para confundir al enemigo.", (byte) 6, (short) 2500, (short) 1200));
        this.mazoGeneral.add(new Monstruo("Bebé Dragón", "Un dragón pequeño con un potencial que aún no ha despertado.", (byte) 3, (short) 1200, (short) 700));
        this.mazoGeneral.add(new Monstruo("Elfo Gemelo", "Hermanas elfas que atacan con una coordinación perfecta.", (byte) 4, (short) 1900, (short) 900));
        this.mazoGeneral.add(new Monstruo("Pared de Ilusión", "Si es atacada, devuelve al monstruo atacante a la mano del dueño.", (byte) 4, (short) 1200, (short) 1850));
        this.mazoGeneral.add(new Monstruo("Jinete de Kaneka", "Un guerrero veloz que monta un ave mecánica.", (byte) 4, (short) 1800, (short) 1600));
        this.mazoGeneral.add(new Monstruo("Kuriboh", "Puedes descartarlo para que el daño de un ataque sea 0.", (byte) 1, (short) 300, (short) 200));
        this.mazoGeneral.add(new Monstruo("Sangan", "Al ir al cementerio, te permite buscar un monstruo débil en tu mazo.", (byte) 3, (short) 1000, (short) 600));
        this.mazoGeneral.add(new Monstruo("Tomate Místico", "Al ser destruido, invoca a otro monstruo de OSCURIDAD del mazo.", (byte) 4, (short) 1400, (short) 1100));
        this.mazoGeneral.add(new Monstruo("Soldado del Brillo Negro", "Un guerrero legendario invocado mediante un ritual antiguo.", (byte) 8, (short) 3000, (short) 2500));
        this.mazoGeneral.add(new Monstruo("Mago de la Fe", "Al voltearse, recupera una carta mágica de tu cementerio.", (byte) 1, (short) 300, (short) 400));
        this.mazoGeneral.add(new Monstruo("Hombre de Frijol Natural", "Un guerrero vegetal con una fuerza física sorprendente.", (byte) 3, (short) 1750, (short) 800));
        this.mazoGeneral.add(new Monstruo("Ciber Dragón", "Puede invocarse gratis si tu oponente tiene monstruos y tú no.", (byte) 5, (short) 2100, (short) 1600));
        this.mazoGeneral.add(new Monstruo("Breaker el Guerrero Mágico", "Al ser invocado, puede destruir una carta mágica o trampa.", (byte) 4, (short) 1600, (short) 1000));
        this.mazoGeneral.add(new Monstruo("Caballero Comandante", "Sube el ataque de todos tus guerreros en 400 puntos.", (byte) 4, (short) 1200, (short) 1900));
        this.mazoGeneral.add(new Monstruo("Lobo de Guerra Negro Pechado", "Un hombre lobo que prohíbe activar trampas durante su ataque.", (byte) 4, (short) 1600, (short) 1200));
        this.mazoGeneral.add(new Monstruo("Insecto Come-Hombres", "Al voltearse boca arriba, destruye un monstruo en el campo.", (byte) 2, (short) 450, (short) 600));
        this.mazoGeneral.add(new Monstruo("Marshmallon", "No puede ser destruido por batalla. Es un muro eterno.", (byte) 3, (short) 300, (short) 500));
        this.mazoGeneral.add(new Monstruo("Neo Alius, Héroe Elemental", "Un héroe versátil que brilla en el campo de batalla.", (byte) 4, (short) 1900, (short) 1300));
        this.mazoGeneral.add(new Monstruo("Pingüino Pesadilla", "Al voltearse, devuelve una carta del oponente a su mano.", (byte) 4, (short) 900, (short) 1800));
        this.mazoGeneral.add(new Monstruo("Golem de Engranaje Antiguo", "Impide que el rival use magias o trampas cuando ataca.", (byte) 8, (short) 3000, (short) 3000));
        this.mazoGeneral.add(new Monstruo("Chica Maga Oscura", "Gana 300 de ataque por cada Mago Oscuro en el cementerio.", (byte) 6, (short) 2000, (short) 1700));
        this.mazoGeneral.add(new Monstruo("Jinzo", "Mientras esté en el campo, nadie puede usar cartas Trampa.", (byte) 6, (short) 2400, (short) 1500));
        this.mazoGeneral.add(new Monstruo("Lobo de Guerra Genético", "Un monstruo normal con un ataque físico devastador.", (byte) 4, (short) 2000, (short) 100));
        this.mazoGeneral.add(new Monstruo("Soldado de la Lanza", "Inflige daño de penetración si ataca a alguien en defensa.", (byte) 4, (short) 1500, (short) 1800));
        this.mazoGeneral.add(new Monstruo("Gearfried el Caballero de Hierro", "Destruye cualquier carta de equipo que intentes ponerle.", (byte) 4, (short) 1800, (short) 1600));
        this.mazoGeneral.add(new Monstruo("Zaborg, el Monarca del Trueno", "Al ser invocado por sacrificio, destruye un monstruo.", (byte) 5, (short) 2400, (short) 1000));
        this.mazoGeneral.add(new Monstruo("Gran Escudo Gardna", "Un muro masivo que niega magias que lo seleccionen.", (byte) 4, (short) 100, (short) 2600));
        this.mazoGeneral.add(new Monstruo("Gilaaurus", "Se invoca especial pero deja al rival revivir algo.", (byte) 3, (short) 1400, (short) 800));
        this.mazoGeneral.add(new Magia("Monstruo Renacido", "Elige un monstruo en cualquier cementerio e invócalo en tu campo.", TipoHabilidadEspecialMagia.MONSTRUO_RENACIDO));
        this.mazoGeneral.add(new Magia("Agujero Negro", "Destruye a todos los monstruos que estén en el campo de batalla.", TipoHabilidadEspecialMagia.AGUJERO_NEGRO));
        this.mazoGeneral.add(new Magia("Tifón del Espacio Místico", "Destruye una carta mágica o de trampa en el campo.", TipoHabilidadEspecialMagia.TIFON_DEL_ESPACIO_MISTICO));
        this.mazoGeneral.add(new Magia("Espadas de la Luz Reveladora", "Impide que los monstruos del oponente ataquen durante 3 turnos.", TipoHabilidadEspecialMagia.ESPADAS_DE_LA_LUZ_REVELADORA));
        this.mazoGeneral.add(new Magia("Olla de la Codicia", "Roba 2 cartas de tu mazo.", TipoHabilidadEspecialMagia.OLLA_DE_LA_CODICIA));
        this.mazoGeneral.add(new Magia("Entierro Insensato", "Envía un monstruo de tu mazo directamente al cementerio.", TipoHabilidadEspecialMagia.ENTIERRO_INSENSATO));
        this.mazoGeneral.add(new Magia("Raigeki", "Destruye todos los monstruos que controle tu oponente.", TipoHabilidadEspecialMagia.RAIGEKI));
        this.mazoGeneral.add(new Magia("Cuchillo de Cazador", "Si controlas al Mago Oscuro, destruye un monstruo del rival.", TipoHabilidadEspecialMagia.MIL_CUCHILLOS));
        this.mazoGeneral.add(new Magia("Unidad", "Suma la defensa de todos tus monstruos para crear un muro gigante.", TipoHabilidadEspecialMagia.UNIDAD));
        this.mazoGeneral.add(new Magia("Polimerización", "Fusiona dos o más monstruos para invocar un monstruo de Fusión.", TipoHabilidadEspecialMagia.POLIMERIZACION));
        this.mazoGeneral.add(new Trampa("Fuerza de Espejo", "Cuando un monstruo del oponente ataca, destruye todos sus monstruos en posición de ataque.", TipoHabilidadEspecialTrampa.FUERZA_DE_ESPEJO, TipoHabilidadEspecialTrampa.BATALLA));
        this.mazoGeneral.add(new Trampa("Cilindro Mágico", "Niega el ataque de un monstruo enemigo e inflige daño a sus LP igual a su ataque.", TipoHabilidadEspecialTrampa.CILINDRO_MAGICO, TipoHabilidadEspecialTrampa.BATALLA));
        this.mazoGeneral.add(new Trampa("Armadura de Sakuretsu", "Cuando un monstruo del oponente ataca, destruye al monstruo atacante.", TipoHabilidadEspecialTrampa.ARMADURA_DE_SAKURETSU, TipoHabilidadEspecialTrampa.BATALLA));
        this.mazoGeneral.add(new Trampa("Tributo Torrencial", "Cuando un monstruo es invocado, destruye todos los monstruos en el campo.", TipoHabilidadEspecialTrampa.TRIBUTO_TORRENCIAL, TipoHabilidadEspecialTrampa.INVOCACION));
        this.mazoGeneral.add(new Trampa("Llamada de los Condenados", "Elige un monstruo de tu cementerio e invócalo en posición de ataque.", TipoHabilidadEspecialTrampa.LLAMADA_DE_LOS_CONDENADOS, TipoHabilidadEspecialTrampa.INVOCACION));
        this.mazoGeneral.add(new Trampa("Sombreros Mágicos", "Elige dos cartas mágicas de tu mazo y escóndelas con un monstruo para confundir al rival.", TipoHabilidadEspecialTrampa.SOMBREROS_MAGICOS, TipoHabilidadEspecialTrampa.INVOCACION));
        this.mazoGeneral.add(new Trampa("Waboku", "Cualquier daño que recibas de los monstruos del oponente este turno se convierte en 0.", TipoHabilidadEspecialTrampa.WABOKU, TipoHabilidadEspecialTrampa.INVOCACION));
        this.mazoGeneral.add(new Trampa("Muro de Espejo", "Reduce el ataque de todos los monstruos enemigos a la mitad.", TipoHabilidadEspecialTrampa.MURO_DE_ESPEJO, TipoHabilidadEspecialTrampa.INVOCACION));
        this.mazoGeneral.add(new Trampa("Represión", "Toma el control de un monstruo del oponente hasta el final de este turno.", TipoHabilidadEspecialTrampa.REPRESION, TipoHabilidadEspecialTrampa.INVOCACION));
        this.mazoGeneral.add(new Trampa("Drenaje de Habilidad", "Paga 1000 LP para negar todos los efectos de los monstruos en el campo.", TipoHabilidadEspecialTrampa.DRENAJE_DE_HABILIDAD, TipoHabilidadEspecialTrampa.INVOCACION));

        Collections.shuffle(mazoGeneral);
    }

    public List<Carta> getMazoGeneral() {
        return mazoGeneral;
    }

    public void setMazoGeneral(List<Carta> mazoGeneral) {
        this.mazoGeneral = mazoGeneral;
    } 

}
