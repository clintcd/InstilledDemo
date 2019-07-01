package company.immersion.demo;

import java.util.ArrayList;

/**
 * This is the entry point into the application.
 * The application accepts arguments from the command line to determine the unique view time (UVT) of video clips viewed
 * by a person.  The command line required to execute the application looks like this:
 *     java -jar InstilledDemo.jar 0-1000 2000-3000 2500-4000
 *
 * By default, the UVT is output when the application is executed.  If the first parameter is the switch '-outputjson',
 * then a json document is output instead.  This output can be used to populate a vis.js timeline component.
 * To checkout populating a vis.js timeline component, just paste the output here:
 * @link https://visjs.org/examples/timeline/dataHandling/dataSerialization.html
 *
 * Application
 *
 * @author  Clint Dalton
 */
public class Main {

    private FragmentGroup group = new FragmentGroup();
    private boolean outputJSON = false;

    public Main(String[] params) {
        processParameters(params);
    }

    /**
     * @param params
     */
    protected void processParameters(String[] params) {

        int paramsStartPos = 0;

        if (params.length > 0) {
            if (params[0].equalsIgnoreCase("-outputjson")) {
                ++paramsStartPos;
                this.outputJSON = true;
            }
        }

        String[] tempArray;
        long beginPos;
        long endPos;
        long length;

        for (int i = paramsStartPos; i < params.length; i++) {
            tempArray = params[i].split("-");
            beginPos = Long.valueOf(tempArray[0]).longValue();
            endPos = Long.valueOf(tempArray[1]).longValue();
            length = endPos - beginPos;

            this.group.addFragment(new Fragment(beginPos, length));
        }
    }

    /**
     * Builds a JSON string representation of the fragment group.  Overlapping fragments are represented as
     * segments that span groups of overlapping fragments.  All fragments in group are represented as well.
     *
     * @return a JSON string representation of fragment group
     */
    protected String generateJSON() {
        StringBuilder sb = new StringBuilder();
        sb.append("[\n");

        // Output segments
        ArrayList<FragmentPosition> segments = group.getUniqueSegmentFragmentPositions();
        long beginPos;
        long endPos;
        for (int i = 0; i < segments.size(); i++) {
            beginPos = segments.get(i).getPosition();
            endPos = beginPos + segments.get(i).getSegmentUniqueViewTime();
            this.appendFragmentJSON(sb, "seg" + (i+1), "Segment " + (i+1),
                    "white", "blue", beginPos, endPos);
            if (i < (segments.size() -1 )) {
                sb.append(",\n");
            }
        }

        // Output positions
        int positionCount = group.getPositionCount();
        if (positionCount > 0) {
            sb.append(",\n");

            for (int i = 0; i < positionCount; i++) {
                // Output fragments at each position
                FragmentPosition fragmentPosition = group.getFragmentPosition(i);
                ArrayList<Fragment> fragments = fragmentPosition.getFragments();
                String contentDescription;
                for (int x = 0; x < fragments.size(); x++) {
                    beginPos = fragments.get(x).getPosition();
                    endPos = beginPos + fragments.get(x).getLength();
                    contentDescription = fragments.get(x).getUuid() + " / " + fragments.get(x).getName();
                    this.appendFragmentJSON(sb, fragments.get(x).getUuid(), contentDescription,
                            "white", "green", beginPos, endPos);
                    if (i < (fragments.size() -1 )) {
                        sb.append(",\n");
                    }
                }

                if (i < positionCount - 1) {
                    sb.append(",\n");
                }
            }
        }

        sb.append("]\n");

        return sb.toString();
    }

    /**
     * Appends data from a fragment or segment to the passed stringbuilder.
     *
     * @param sb
     * @param id
     * @param content
     * @param textColor
     * @param backgroundColor
     * @param start
     * @param end
     */
    protected void appendFragmentJSON(StringBuilder sb, String id, String content,
                                      String textColor, String backgroundColor, long start, long end) {

        sb.append(
                "  {\n" +
                "    \"id\": \"" + id + "\",\n" +
                "    \"content\": \"" + content + "\",\n" +
                "    \"style\":  \"color: " + textColor +  "; background-color: " + backgroundColor + ";\",\n" +
                "    \"start\": " + start + ",\n" +
                "    \"end\": " + end + "\n" +
                "  }\n"
        );
    }

    /**
     * The main entry point for the application.
     *
     * @param args
     */
    public static void main(String[] args) {

        Main app = new Main(args);

        if (app.outputJSON == false) {
            System.out.println(app.group.getUniqueViewTime());
        }
        else {
            System.out.println(app.generateJSON());
        }
    }
}
