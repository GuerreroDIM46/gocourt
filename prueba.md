I'm a beginner in Spring and I'm trying to use a @PrePersist listener to validate a Partido entity before it is saved to the database. However, I keep encountering the error: java.lang.NullPointerException: Cannot invoke "es.mde.repositorios.Partido.getCampo()" because "this.partido" is null. I don't know what I'm doing wrong.

Here is my current code:

    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Component;

    import javax.persistence.PrePersist;
    import java.time.LocalDateTime;
    import java.util.List;
    import java.util.stream.Collectors;

    @Component
    public class PartidoListener {

        @Autowired
        private PartidoDAO partidoDAO; 

        @PrePersist
        public void validacionPartido(Partido partido) {
            System.err.println("punto de fallo: 1");
            Long partidoCampoId = partido.getCampo().getId();
            LocalDateTime partidoCuando = partido.getCuando();
            System.err.println("datos de partido: " + partidoCampoId + ", " + partidoCuando );
            System.err.println("punto de fallo: 2");
            try {
                List<Partido> partidosCoincidentes = partidoDAO.findAll()
                        .stream()
                        .filter(p -> p.getCuando().equals(partidoCuando))
                        .filter(p -> p.getCampo().getId().equals(partidoCampoId))
                        .collect(Collectors.toList());
                System.err.println("punto de fallo: 3");
                boolean valido = partidosCoincidentes.isEmpty();
                System.err.println("punto de fallo: 4");
                System.err.println("partido es valido: " + valido);
                System.err.println("punto de fallo: 5");
            } catch (Exception e) {
                System.err.println(e);
            }
        }
    }

Error:

    java.lang.NullPointerException: Cannot invoke "es.mde.repositorios.Partido.getCampo()" because "this.partido" is null

Additional Information:
- I have annotated my Partido entity with @EntityListeners(PartidoListener.class).
- The Partido class has a Campo field, which is another entity.
What am I doing wrong? How can I fix this issue? If you need more information or code snippets, please let me know.

Thank you!

Partido.class is like that:

    @Entity
    @Table(name = "PARTIDOS")
    @EntityListeners(PartidoListener.class)
    public class Partido {
        
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(unique = true)  
        private Long id;
        
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "CAMPO")
        private Campo campo;
        private LocalDateTime cuando;
        
        @OneToMany(cascade = CascadeType.ALL, targetEntity = Puntuacion.class, mappedBy = "partido")
        private Collection<Puntuacion> puntuaciones = new ArrayList<>();

        ...
    }

Campo.class is:

    @Entity
    @Table(name = "CAMPOS")
    public class Campo {
        
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(unique = true)  
        private Long id;
        
        private String nombre, provincia;
        private float valorCampo, valorSlope;
        
        @OneToMany(cascade = CascadeType.ALL, targetEntity = Jugador.class, mappedBy = "campo")
        private Collection<Jugador> jugadores = new ArrayList<>();
        
        @OneToMany(cascade = CascadeType.ALL, targetEntity = Partido.class, mappedBy = "campo")
        private Collection<Partido> partidos = new ArrayList<>();
        ...
    }