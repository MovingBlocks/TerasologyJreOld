import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.tukaani.xz.LZMA2Options
import org.tukaani.xz.XZOutputStream

class CompressXzTask extends DefaultTask {

    @Input
    File inputFile

    @TaskAction
    public void run() {

        File outputFile = new File(inputFile.getAbsolutePath() + ".xz");

        inputFile.withInputStream { is ->
            outputFile.withOutputStream { os ->
                def lzmaOptions = new LZMA2Options();
                new XZOutputStream(os, lzmaOptions).withStream { out ->
                    byte[] buffer = new byte[4096];
                    int read;
                    while ((read = is.read(buffer)) != -1) {
                        out.write(buffer, 0, read);
                    }
                }
            }
        }

        inputFile.delete();

    }

}
