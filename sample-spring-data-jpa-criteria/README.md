# Spring Data Jpa Criteria

Spring Data JPA with criteria implementation

## Introduction

## Add maven dependency

```xml
<dependency>
    <groupId>com.github.ydespreaux.spring.data</groupId>
    <artifactId>spring-data-jpa-criteria</artifactId>
    <version>1.2.0</version>
</dependency>
```

## Quick Start with Spring Boot

### Add SpringBoot application

```java
@SpringBootApplication
public class SampleJpaCriteriaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SampleJpaCriteriaApplication.class, args);
    }
}
```

### Add domains

#### Artist model

```java
@Getter
@Setter
@Entity
@Table(name = "ARTIST")
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(
            name = "native",
            strategy = "native"
    )
    private Integer id;

    @Column(name = "DISPLAY_NAME", nullable = false)
    private String displayName;

    @OneToMany(mappedBy = "artist", cascade = CascadeType.ALL)
    private Set<Album> albums;
}
```

#### Album model

```java
@Getter
@Setter
@Entity
@Table(name = "ALBUM")
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(
            name = "native",
            strategy = "native"
    )
    private Integer id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Integer year;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ARTIST_ID", nullable = false, foreignKey = @ForeignKey(name = "FK_ALBUM_ARTIST_ID"))
    private Artist artist;

    @OneToMany(mappedBy = "album", cascade = {CascadeType.ALL})
    private List<Song> songs;
}
```

#### Song model

```java
@Getter
@Setter
@Entity
@Table(name = "SONG")
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(
            name = "native",
            strategy = "native"
    )
    private Integer id;

    @Column(name = "TRACK", nullable = false)
    private Integer track;

    @Column(name = "TITLE", nullable = false, length = 150)
    private String title;

    /**
     * the workstations property.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ALBUM_ID", foreignKey = @ForeignKey(name = "FK_SONG_ALBUM_ID"))
    private Album album;
}
```

### Add repositories

```java
public interface ArtistRepository extends JpaCriteriaRepository<Artist, Integer> {
}
```

```java
public interface AlbumRepository extends JpaCriteriaRepository<Album, Integer> {
}
```

```java
public interface SongRepository extends JpaCriteriaRepository<Song, Integer> {
}
```

### Add configuration

```java
@Configuration
@EnableAutoConfiguration
@EntityScan(basePackages = {"com.github.ydespreaux.sample.jpa.criteria.domain"})
@EnableJpaCriteriaRepositories(basePackages = {"com.github.ydespreaux.sample.jpa.criteria.repository"})
public class JpaConfiguration {
}
```

### Add a rest controller

```java
@RestController
@RequestMapping("/api/songs")
public class SongRestController {

    @Autowired
    private SongRepository repository;

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Page<SongRestController.SongDetail>> findSongByQuery(
            @RequestParam(required = false, value = "title") String title,
            @RequestParam(required = false, value = "artist") String artist,
            @RequestParam(required = false, value = "album") String album,
            @RequestParam(required = false, value = "year") Integer year,
            Pageable pageable) {

        Criteria criteria = new Criteria();
        if (StringUtils.hasText(title)) {
            criteria = criteria.and("title").contains(title);
        }
        if (StringUtils.hasText(artist)) {
            criteria = criteria.and("album.artist.displayName").contains(artist);
        }
        if (StringUtils.hasText(album)) {
            criteria = criteria.and("album.title").contains(album);
        }
        if (year != null) {
            criteria = criteria.and("album.year").eq(year);
        }
        // Add the default sort if the pageable is unsorted
        if (pageable.getSort().isUnsorted()) {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.ASC, "title"));
        }
        return ResponseEntity.ok(this.repository.findAll(criteria, pageable, new QueryOptions().withAssociation("album", "album.artist")).map(this::mapDetail));
    }

    /**
     * @param song
     * @return
     */
    private SongDetail mapDetail(Song song) {
        return SongDetail.builder()
                .id(song.getId())
                .artist(song.getAlbum().getArtist().getDisplayName())
                .album(song.getAlbum().getTitle())
                .year(song.getAlbum().getYear())
                .track(song.getTrack())
                .title(song.getTitle())
                .build();
    }

    @Data
    @Builder
    private static class SongDetail {
        private Integer id;
        private String artist;
        private String album;
        private Integer year;
        private Integer track;
        private String title;
    }
}
```

## Run application

```bash
mvn clean install
mvn spring-boot:run
```

Search all songs containing '400 years' in the title of the song

```bash
curl http://localhost:8080/api/songs?title=400%20years
{
    "content": [
        {
            "id": 27,
            "artist": "Bob Marley and the Wailers",
            "album": "Catch a Fire",
            "year": 1973,
            "track": 3,
            "title": "400 Years"
        },
        {
            "id": 21,
            "artist": "Bob Marley and the Wailers",
            "album": "Soul Rebels",
            "year": 1970,
            "track": 9,
            "title": "400 Years"
        }
    ],
    "pageable": {
        "sort": {
            "sorted": true,
            "unsorted": false,
            "empty": false
        },
        "offset": 0,
        "pageSize": 20,
        "pageNumber": 0,
        "unpaged": false,
        "paged": true
    },
    "totalElements": 2,
    "totalPages": 1,
    "last": true,
    "size": 20,
    "number": 0,
    "sort": {
        "sorted": true,
        "unsorted": false,
        "empty": false
    },
    "numberOfElements": 2,
    "first": true,
    "empty": false
}
```

