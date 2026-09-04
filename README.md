# GiVeU

GiVeU è un'applicazione mobile Android progettata per facilitare la donazione gratuita e il riuso di oggetti inutilizzati, favorendo la solidarietà e lo scambio tra utenti.

## Scopo
L'app permette di consultare oggetti disponibili per la donazione, organizzarli per categoria, visualizzarne i dettagli e inserire nuovi oggetti con descrizione e immagine.
Mockup: https://www.figma.com/design/baeImqYTgnaIB4ypMeuvlq/Untitled?node-id=31-129&t=gz0gYkkSVQ93XafX-1

<img width="2234" height="915" alt="mockup.png" src="https://github.com/user-attachments/assets/a4d4517b-1082-4425-a99a-bbff5039f1e4" />

## Funzionalità
- Home con categorie.
- Lista degli articoli per categoria.
- Dettaglio con immagine, descrizione, telefono e posizione.
- Pulsante **Chiama** tramite dialer.
- Apertura della posizione tramite applicazione compatibile con mappe.
- Inserimento di nuovi articoli.
- Foto tramite fotocamera o galleria.
- Salvataggio locale tramite Room.
- Recupero dati da API remote.
- Aggiornamento periodico tramite WorkManager.

## Architettura
Il progetto segue il principio di **Separation of Concerns** ed è diviso in quattro moduli:

```text
GiVeU/
├── app/
├── ui/
├── data/
├── domain/
└── gradle/
```

- **ui**: Activity, Adapter e ViewModel.
- **domain**: modelli, repository astratti e use case (ciascuno separato in interfaccia e implementazione).
- **data**: database Room, DAO, API Retrofit, repository e WorkManager.
- **app**: configurazione principale e Manifest.

## Lifecycle e concorrenza
`ArticleViewModel` utilizza `ViewModel` e `StateFlow` per esporre in modo reattivo gli articoli alla UI, combinando i dati locali (osservati come `Flow` da Room) con quelli recuperati da remoto. Le Activity raccolgono lo `StateFlow` tramite `repeatOnLifecycle`, restando sincronizzate con il ciclo di vita della schermata. Le operazioni di salvataggio e recupero remoto sono gestite con Kotlin Coroutines, `viewModelScope` e `Dispatchers.IO`, evitando di bloccare il Main Thread.

## API remote
- **DummyJSON**: recupero di prodotti per categoria.
- **Open Library**: recupero di dati relativi ai libri.

Le chiamate sono implementate con Retrofit e funzioni `suspend`.

## Storage locale
La persistenza locale è realizzata con **Room**, tramite `AppDatabase`, `ArticleDao` ed entità locali. Gli articoli vengono osservati come `Flow`, restando sincronizzati con la UI non appena i dati locali cambiano.

## Permessi
- `CAMERA` per scattare fotografie.
- Permessi per le immagini/galleria.
- `INTERNET` per le API remote.

Le funzionalità multimediali utilizzano `ActivityResultContracts`.

## WorkManager
Il progetto utilizza `ArticleRefreshWorker`, un `CoroutineWorker`, per eseguire periodicamente attività in background.

## Tecnologie
Kotlin, Android SDK, AndroidX, ViewModel, StateFlow, Coroutines, Retrofit, Gson, Room, WorkManager e Activity Result API.

## Email
assmaa.matouk@studio.unibo.it
