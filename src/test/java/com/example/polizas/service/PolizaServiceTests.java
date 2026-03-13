@ExtendWith(MockitoExtension.class)
class PolizaServiceTest {

    @Mock
    PolizaRepository polizaRepository;

    @Mock
    RiesgoRepository riesgoRepository;

    @InjectMocks
    PolizaService service;

    @Test
    void renovarPoliza() {

        Poliza p = new Poliza();
        p.setEstado(EstadoPoliza.ACTIVA);
        p.setCanon(100.0);
        p.setPrima(100.0);

        when(polizaRepository.findById(1L)).thenReturn(Optional.of(p));

        service.renovar(1L);

        assertEquals(110.0, p.getCanon());
        assertEquals(EstadoPoliza.RENOVADA, p.getEstado());
    }

    @Test
    void noRenovarCancelada() {

        Poliza p = new Poliza();
        p.setEstado(EstadoPoliza.CANCELADA);

        when(polizaRepository.findById(1L)).thenReturn(Optional.of(p));

        assertThrows(RuntimeException.class,
                () -> service.renovar(1L));
    }
}
