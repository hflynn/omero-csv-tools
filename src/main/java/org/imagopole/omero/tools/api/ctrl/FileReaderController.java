package org.imagopole.omero.tools.api.ctrl;

import java.io.IOException;

import omero.ServerError;

import org.imagopole.omero.tools.api.cli.Args.ContainerType;
import org.imagopole.omero.tools.api.dto.CsvData;

/**
 * Dispatcher layer to the file related services.
 *
 * @author seb
 *
 */
public interface FileReaderController {

    /**
     * Read a CSV file content either from the local filesystem or from an OMERO attached original file.
     *
     * @param experimenterId the experimenter
     * @param containerId the container ID used to locate the file (local or remote)
     * @param fileContainerType the type of container used to locate the file (local or remote)
     * @param fileName the file name (may be a path for local files)
     * @return the file content as String
     * @throws ServerError OMERO client or server failure
     * @throws IOException read failure
     */
    CsvData readByFileContainerType(
                    Long experimenterId,
                    Long containerId,
                    ContainerType fileContainerType,
                    String fileName) throws ServerError, IOException;

}
