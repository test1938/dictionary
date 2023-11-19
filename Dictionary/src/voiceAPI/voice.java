package voiceAPI;

import com.voicerss.tts.AudioFormat;
import com.voicerss.tts.VoiceParameters;
import com.voicerss.tts.VoiceProvider;
import sun.audio.*;
import java.io.*;
import java.util.HashMap;

public class voice {
    private static final String API_KEY = "bb67de4f732d4af7b9984dd3a1e0cd67";
    private static final String PATH = "src/data/audio.wav";
    public static HashMap<String, String> voiceName= new HashMap<String, String>() {{
        put("en-gb", "Nancy");
        put("en-us", "Linda");
    }};
    public static void speak(String word, String language) throws Exception {
        VoiceProvider tts = new VoiceProvider(API_KEY);
        VoiceParameters params = new VoiceParameters(word, AudioFormat.Format_44KHZ.AF_44khz_16bit_stereo);
        params.setBase64(false);
        params.setLanguage(language);
        params.setVoice(voiceName.get(language));
        params.setRate(2);

        byte[] voice = tts.speech(params);

        FileOutputStream fos = new FileOutputStream(PATH);
        fos.write(voice, 0, voice.length);
        fos.flush();
        fos.close();

        InputStream input = new FileInputStream(PATH);
        AudioStream audio = new AudioStream(input);
        AudioPlayer.player.start(audio);
    }
}
