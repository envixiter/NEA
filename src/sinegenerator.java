import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class sinegenerator {
        public static void generateAndPlaySineWave(double frequency, double duration,
                                                   float sampleRate, double amplitude)
                throws LineUnavailableException {
            int totalSamples = (int) (duration * sampleRate);
            byte[] audioBuffer = new byte[totalSamples * 2];
            for (int i = 0; i < totalSamples; i++) {
                //time in seconds
                double time = i / sampleRate;
                double sineValue = amplitude * Math.sin(2 * Math.PI * frequency * time);
                short sample = (short) (sineValue * Short.MAX_VALUE);
                ByteBuffer byteBuffer = ByteBuffer.allocate(2)
                        .order(ByteOrder.BIG_ENDIAN)
                        .putShort(sample);
                byte[] sampleBytes = byteBuffer.array();
                audioBuffer[2 * i] = sampleBytes[0];
                audioBuffer[2 * i + 1] = sampleBytes[1];
            }

            AudioFormat format = new AudioFormat(
                    sampleRate,       // Sample rate
                    16,               // Sample size in bits (16-bit)
                    1,                // Channels (1 = mono, 2 = stereo)
                    true,             // Signed (true for PCM)
                    true              // Big-endian (required for 16-bit PCM)
            );

            try (SourceDataLine line = AudioSystem.getSourceDataLine(format)) {
                line.open(format);
                line.start();
                line.write(audioBuffer, 0, audioBuffer.length);
                System.out.println("sound played");
                line.drain();
                line.stop();
            }
        }
}
