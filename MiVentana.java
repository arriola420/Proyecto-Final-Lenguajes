// Importaciones y paquetes
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.regex.*;
import java.io.*;
import java.util.*;

public class MiVentana extends JFrame {
    // Variables para guardar el texto y los números de palabras, emojis y creación del diccionario de emojis
    private String textoGuardado = "";
    private int[] resultado;
    private Map<String, String> emojisMap = new HashMap<>();
    public MiVentana() {
        emojisMap.put(":)", "rC:\\Users\\Tuvalu\\Desktop\\Proyectos Universidad\\Java\\ProyectofinalLenguajes\\src\\emojis\\003-feliz.png");
        emojisMap.put(":(", "C:\\Users\\Tuvalu\\Desktop\\Proyectos Universidad\\Java\\ProyectofinalLenguajes\\src\\emojis\\009-triste.png");

        // Configuración ventana principal
        setTitle("PROYECTO FINAL - LENGUAJES DE PROGRAMACIÓN");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Configuración textos e imagen.
        Font fuente = new Font("Arial", Font.BOLD, 30);
        JLabel etiquetaImagen = getImage("C:/Users/Tuvalu/Desktop/Proyectos Universidad/Java/ProyectofinalLenguajes/src/logo_eafit_completo.png");
        add(etiquetaImagen);

        JLabel tituloEafit = new JLabel("Universidad Eafit Proyecto Final");
        tituloEafit.setBounds(300, 35, 1000, 30);
        tituloEafit.setFont(fuente);
        add(tituloEafit);

        JLabel subtitulo = new JLabel("Lenguajes de Programación");
        subtitulo.setBounds(300, 75, 1000, 30);
        subtitulo.setFont(fuente);
        add(subtitulo);

        JLabel jentrada = new JLabel("Ingrese un texto: ");
        jentrada.setBounds(50, 175, 1000, 30);
        jentrada.setFont(fuente);
        add(jentrada);

        JLabel jsalida = new JLabel("Salida: ");
        jsalida.setBounds(125, 350, 1000, 30);
        jsalida.setFont(fuente);
        add(jsalida);

        JLabel respuesta = new JLabel("");
        respuesta.setBounds(140, 440, 1000, 30);
        respuesta.setFont(fuente);
        add(respuesta);
        respuesta.setForeground(Color.GREEN);

        JLabel respuesta2 = new JLabel("");
        respuesta2.setBounds(250, 350, 1000, 30);
        respuesta2.setFont(fuente);
        add(respuesta2);
        respuesta2.setForeground(Color.GREEN);

        // Configuración input, botón y acción del botón
        JTextField campoTexto = new JTextField();
        campoTexto.setBounds(400, 177, 300, 30);
        add(campoTexto);

        JButton miBoton = new JButton("Procesar Cadena de texto");
        miBoton.setBounds(450, 250, 200, 30);
        add(miBoton);

        miBoton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textoGuardado = campoTexto.getText();
                resultado = analizador_lexicografico(textoGuardado);
                respuesta.setText("Se encontraron " + resultado[1] + " palabras y " + resultado[0] + " emojis");
            }
        });

    }

    // Método para analizar el texto ingresado
    public int[] analizador_lexicografico(String texto) {
        // Define el patrón para identificar emojis
        String patronEmojis = "(:\\)|:\\(|:D|;-\\)|:P|xD|:-\\)|:-\\(|\\(y\\)|\\(n\\)|<3|\\\\m/|:-O|:O|:-\\||:\\||:\\*|>:\\(|\\^\\^|:-\\])";
        Pattern patternEmojis = Pattern.compile(patronEmojis);
        Matcher matcherEmojis = patternEmojis.matcher(texto);
        int[] resultados = new int[2];

        // Utiliza un patrón para identificar palabras en español
        Pattern patternPalabras = Pattern.compile("\\b[a-zA-ZáéíóúüÁÉÍÓÚÜñÑ]+\\b");
        Matcher matcherPalabras = patternPalabras.matcher(texto);
        String textoConEmojisReemplazados = texto;

        while (matcherEmojis.find()) {
            resultados[0]++;
            String emojiEncontrado = matcherEmojis.group();
            String rutaImagen = emojisMap.get(emojiEncontrado);

            textoConEmojisReemplazados = textoConEmojisReemplazados.replace(emojiEncontrado, "<img src='" + rutaImagen + "' alt='" + emojiEncontrado + "' width='40' height='40'>");
            respuesta2.setText(textoConEmojisReemplazados);
        }
        while (matcherPalabras.find()) {
            resultados[1]++;
        }
        return resultados;
    }

    // Método para cargar el diccionario de palabras en español
    private Set<String> cargarDiccionarioEspanol(String archivo) {
        Set<String> diccionario = new HashSet<>();
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                diccionario.add(linea.toLowerCase());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return diccionario;
    }

    // Método para cargar la imagen
    private static JLabel getImage(String ruta) {
        ImageIcon imagenIcon = new ImageIcon(ruta);
        Image imagenOriginal = imagenIcon.getImage();
        Image imagenEscalada = imagenOriginal.getScaledInstance(200, -1, Image.SCALE_DEFAULT);
        ImageIcon imagenEscaladaIcon = new ImageIcon(imagenEscalada);

        JLabel etiquetaImagen = new JLabel(imagenEscaladaIcon);
        etiquetaImagen.setBounds(50, 35, 200, imagenEscaladaIcon.getIconHeight());
        return etiquetaImagen;
    }

    // Método main
    public static void main(String[] args) {
        // Implementación de la ventana
        MiVentana ventana = new MiVentana();
        ventana.setVisible(true);
    }
}