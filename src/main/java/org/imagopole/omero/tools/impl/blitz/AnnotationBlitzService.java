/**
 *
 */
package org.imagopole.omero.tools.impl.blitz;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Lists;

import omero.ServerError;
import omero.api.ServiceFactoryPrx;
import omero.model.Annotation;
import omero.model.FileAnnotation;
import omero.model.IObject;
import omero.model.TagAnnotation;

import org.imagopole.omero.tools.api.blitz.OmeroAnnotationService;
import org.imagopole.omero.tools.util.BlitzUtil;
import org.imagopole.omero.tools.util.Check;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pojos.DataObject;
import pojos.FileAnnotationData;
import pojos.TagAnnotationData;

/**
 * Service layer to the underlying metadata related OMERO gateway.
 *
 * @author seb
 *
 */
public class AnnotationBlitzService implements OmeroAnnotationService {

    /** Application logs */
    private final Logger log = LoggerFactory.getLogger(AnnotationBlitzService.class);

    /** OMERO Ice session */
    private ServiceFactoryPrx session;

    /**
     * Parameterized constructor.
     *
     * @param session the OMERO Blitz session
     */
    public AnnotationBlitzService(ServiceFactoryPrx session) {
        super();

        Check.notNull(session, "session");
        this.session = session;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public Map<Long, Collection<FileAnnotationData>> listFilesAttachedToContainer(
            Class<? extends IObject> containerClass,
            Long containerId) throws ServerError {

        Check.notNull(containerId, "containerId");
        Check.notNull(containerClass, "containerClass");

        Map<Long, Collection<FileAnnotationData>> result = Collections.emptyMap();

        Map<Long, List<Annotation>> fileAnnotationsLinkedToContainers =
            getSession().getMetadataService().loadSpecifiedAnnotationsLinkedTo(
                        FileAnnotation.class.getName(),
                        null,
                        null,
                        containerClass.getName(),
                        Lists.newArrayList(containerId),
                        null);

        if (null != fileAnnotationsLinkedToContainers) {

            result = DataObject.asPojos(fileAnnotationsLinkedToContainers);

        }

        log.debug("fileAnnotationsLinkedToContainers: id={} type={} list={}",
                   containerId, containerClass.getName(), result);

        return result;
    }

   /**
    * {@inheritDoc}
    */
    @Override
    @SuppressWarnings("unchecked")
    public Collection<TagAnnotationData> listTagsByExperimenter(Long experimenterId) throws ServerError {
         Check.notNull(experimenterId, "experimenterId");

         Set<TagAnnotationData> result = Collections.emptySet();

         List<Annotation> modelAnnotationObjects =
             getSession().getMetadataService().loadSpecifiedAnnotations(
                     TagAnnotation.class.getName(),
                     null,
                     null,
                     BlitzUtil.byExperimenter(experimenterId));

         if (null != modelAnnotationObjects) {

            result = DataObject.asPojos(modelAnnotationObjects);

         }

         log.debug("Found {} tags for experimenter {}", result.size(), experimenterId);

         return result;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public Map<Long, Collection<TagAnnotationData>> listTagsLinkedToContainers(
                    Long experimenterId,
                    Collection<Long> containersIds,
                    Class<? extends IObject> containerClass) throws ServerError {

        Check.notNull(experimenterId, "experimenterId");
        Check.notEmpty(containersIds, "containersIds");
        Check.notNull(containerClass, "containerClass");

        Map<Long, Collection<TagAnnotationData>> result = Collections.emptyMap();

        Map<Long, List<Annotation>> annotationsAlreadyLinkedToObjects =
            getSession().getMetadataService().loadSpecifiedAnnotationsLinkedTo(
                TagAnnotation.class.getName(),
                null,
                null,
                containerClass.getName(),
                Lists.newArrayList(containersIds),
                BlitzUtil.byExperimenter(experimenterId));

        if (null != annotationsAlreadyLinkedToObjects) {

            result=  DataObject.asPojos(annotationsAlreadyLinkedToObjects);

        }

        return result;
    }

    /**
     * Returns session.
     * @return the session
     */
    public ServiceFactoryPrx getSession() {
        return session;
    }

    /**
     * Sets session.
     * @param session the session to set
     */
    public void setSession(ServiceFactoryPrx session) {
        this.session = session;
    }

}
