/*
 * Copyright (C) 2018 Yoann Despréaux
 *
 * This program eq free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program eq distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; see the file COPYING . If not, write to the
 * Free Software Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 *
 * Please send bugreports with examples or suggestions to yoann.despreaux@believeit.fr
 */

package com.github.ydespreaux.sample.jpa.criteria.utils;

import com.github.ydespreaux.sample.jpa.criteria.domain.Album;
import com.github.ydespreaux.sample.jpa.criteria.domain.Artist;
import com.github.ydespreaux.sample.jpa.criteria.domain.Track;
import com.github.ydespreaux.sample.jpa.criteria.repository.AlbumRepository;
import com.github.ydespreaux.sample.jpa.criteria.repository.ArtistRepository;
import com.github.ydespreaux.sample.jpa.criteria.repository.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

public class GenerateDataBuilder {

    @Autowired
    private TrackRepository trackRepository;

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private ArtistRepository artistRepository;

    /**
     * @return
     */
    public static List<Artist> generateArtists() {
        return Arrays.asList(
                buildBobMarleyAndTheWailers(),
                buildRollingStones()
        );
    }

    private static Artist buildBobMarleyAndTheWailers() {
        Artist artist = new Artist();
        artist.setDisplayName("Bob Marley and the Wailers");

        Album theWailingWailers = new Album();
        theWailingWailers.setTitle("The Wailing Wailers");
        theWailingWailers.setYear(1965);
        theWailingWailers.addTrack(Track.builder().track(1).title("I'm Gonna Put It On").build());
        theWailingWailers.addTrack(Track.builder().track(2).title("I Need You").build());
        theWailingWailers.addTrack(Track.builder().track(3).title("Lonesome Feeling").build());
        theWailingWailers.addTrack(Track.builder().track(4).title("What's New Pussycat").build());
        theWailingWailers.addTrack(Track.builder().track(5).title("One Love").build());
        theWailingWailers.addTrack(Track.builder().track(6).title("When The Well Runs Dry").build());
        theWailingWailers.addTrack(Track.builder().track(7).title("Ten Commandments Of Love").build());
        theWailingWailers.addTrack(Track.builder().track(8).title("Rude Boy").build());
        theWailingWailers.addTrack(Track.builder().track(9).title("It Hurts To Be Alone").build());
        theWailingWailers.addTrack(Track.builder().track(10).title("Love And Affection").build());
        theWailingWailers.addTrack(Track.builder().track(11).title("I'm Still Waiting").build());
        theWailingWailers.addTrack(Track.builder().track(12).title("Simmer Down").build());

        Album soulRebels = new Album();
        soulRebels.setTitle("Soul Rebels");
        soulRebels.setYear(1970);
        soulRebels.addTrack(Track.builder().track(1).title("Soul Rebel").build());
        soulRebels.addTrack(Track.builder().track(2).title("Try Me").build());
        soulRebels.addTrack(Track.builder().track(3).title("It's Alright").build());
        soulRebels.addTrack(Track.builder().track(4).title("No Sympathy").build());
        soulRebels.addTrack(Track.builder().track(5).title("My Cup").build());
        soulRebels.addTrack(Track.builder().track(6).title("Soul Almighty").build());
        soulRebels.addTrack(Track.builder().track(7).title("Rebel's Hop").build());
        soulRebels.addTrack(Track.builder().track(8).title("Corner Stone").build());
        soulRebels.addTrack(Track.builder().track(9).title("400 Years").build());
        soulRebels.addTrack(Track.builder().track(10).title("No Water").build());
        soulRebels.addTrack(Track.builder().track(11).title("Reaction").build());
        soulRebels.addTrack(Track.builder().track(12).title("My Sympathy").build());

        Album catchAFire = new Album();
        catchAFire.setTitle("Catch a Fire");
        catchAFire.setYear(1973);
        catchAFire.addTrack(Track.builder().track(1).title("Concrete Jungle").build());
        catchAFire.addTrack(Track.builder().track(2).title("Slave Driver").build());
        catchAFire.addTrack(Track.builder().track(3).title("400 Years").build());
        catchAFire.addTrack(Track.builder().track(4).title("Stop That Train").build());
        catchAFire.addTrack(Track.builder().track(5).title("Baby We've Got A Date").build());
        catchAFire.addTrack(Track.builder().track(6).title("Stir It Up").build());
        catchAFire.addTrack(Track.builder().track(7).title("Kinky Reggae").build());
        catchAFire.addTrack(Track.builder().track(8).title("No More Trouble").build());
        catchAFire.addTrack(Track.builder().track(9).title("Midnight Ravers").build());

        artist.addAlbum(theWailingWailers);
        artist.addAlbum(soulRebels);
        artist.addAlbum(catchAFire);
        return artist;
    }

    private static Artist buildRollingStones() {
        Artist artist = new Artist();
        artist.setDisplayName("The Rolling Stones");

        Album stickyFingers = new Album();
        stickyFingers.setTitle("Sticky Fingers");
        stickyFingers.setYear(1971);
        stickyFingers.addTrack(Track.builder().track(1).title("Brown Sugar").build());
        stickyFingers.addTrack(Track.builder().track(2).title("Sway").build());
        stickyFingers.addTrack(Track.builder().track(3).title("Wild Horses").build());
        stickyFingers.addTrack(Track.builder().track(4).title("Can't You Hear Me Knocking").build());
        stickyFingers.addTrack(Track.builder().track(5).title("You Gotta Move").build());
        stickyFingers.addTrack(Track.builder().track(6).title("Bitch").build());
        stickyFingers.addTrack(Track.builder().track(7).title("I Got The Blues").build());
        stickyFingers.addTrack(Track.builder().track(8).title("Sister Morphine").build());
        stickyFingers.addTrack(Track.builder().track(9).title("Dead Flowers").build());
        stickyFingers.addTrack(Track.builder().track(10).title("Moonlight Mile").build());

        Album steelWheels = new Album();
        steelWheels.setTitle("Steel Wheels");
        steelWheels.setYear(1989);
        steelWheels.addTrack(Track.builder().track(1).title("Sad Sad Sad").build());
        steelWheels.addTrack(Track.builder().track(2).title("Mixed Emotions").build());
        steelWheels.addTrack(Track.builder().track(3).title("Terrifying").build());
        steelWheels.addTrack(Track.builder().track(4).title("Hold on to Your Hat").build());
        steelWheels.addTrack(Track.builder().track(5).title("Hearts for Sale").build());
        steelWheels.addTrack(Track.builder().track(6).title("Blinded by Love").build());
        steelWheels.addTrack(Track.builder().track(7).title("Rock and a Hard Place").build());
        steelWheels.addTrack(Track.builder().track(8).title("Can't Be Seen").build());
        steelWheels.addTrack(Track.builder().track(9).title("Almost Hear You Sigh").build());
        steelWheels.addTrack(Track.builder().track(10).title("Continental Drift").build());
        steelWheels.addTrack(Track.builder().track(11).title("Break the Spell").build());
        steelWheels.addTrack(Track.builder().track(12).title("Slipping Away").build());

        Album voodooLounge = new Album();
        voodooLounge.setTitle("Voodoo Lounge");
        voodooLounge.setYear(1994);
        voodooLounge.addTrack(Track.builder().track(1).title("Love Is Strong").build());
        voodooLounge.addTrack(Track.builder().track(2).title("You Got Me Rocking").build());
        voodooLounge.addTrack(Track.builder().track(3).title("Sparks Will Fly").build());
        voodooLounge.addTrack(Track.builder().track(4).title("The Worst").build());
        voodooLounge.addTrack(Track.builder().track(5).title("New Faces").build());
        voodooLounge.addTrack(Track.builder().track(6).title("Moon Is Up").build());
        voodooLounge.addTrack(Track.builder().track(7).title("Out of Tears").build());
        voodooLounge.addTrack(Track.builder().track(8).title("I Go Wild").build());
        voodooLounge.addTrack(Track.builder().track(9).title("Brand New Car").build());
        voodooLounge.addTrack(Track.builder().track(10).title("Sweethearts Together").build());
        voodooLounge.addTrack(Track.builder().track(11).title("Suck on the Jugular").build());
        voodooLounge.addTrack(Track.builder().track(12).title("Blinded by Rainbows").build());
        voodooLounge.addTrack(Track.builder().track(13).title("Baby Break It Down").build());
        voodooLounge.addTrack(Track.builder().track(14).title("Thru and Thru").build());
        voodooLounge.addTrack(Track.builder().track(15).title("Mean Disposition").build());

        artist.addAlbum(stickyFingers);
        artist.addAlbum(steelWheels);
        artist.addAlbum(voodooLounge);
        return artist;
    }

    public void cleanData() {
        trackRepository.deleteAllInBatch();
        albumRepository.deleteAllInBatch();
        artistRepository.deleteAllInBatch();
    }

    public List<Artist> insertData() {
        return this.artistRepository.saveAll(generateArtists());
    }

}
