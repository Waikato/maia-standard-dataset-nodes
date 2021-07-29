package māia.topology.node.standard.ml.dataset

import māia.configure.Configurable
import māia.configure.ConfigurationElement
import māia.configure.asReconfigureBlock
import māia.configure.subconfiguration
import māia.ml.dataset.DataBatch
import māia.ml.dataset.arff.ARFFLoader
import māia.ml.dataset.arff.ARFFLoaderConfiguration
import māia.ml.dataset.view.DataBatchView
import māia.topology.Node
import māia.topology.NodeConfiguration
import māia.topology.node.base.Source

/**
 * TODO: What class does.
 *
 * @author Corey Sterling (csterlin at waikato dot ac dot nz)
 */
@Node.WithMetadata("Produces a single data-set read from an ARFF file")
class ARFFSource : Source<ARFFSourceConfiguration, DataBatch<*, *>> {

    @Configurable.Register<ARFFSource, ARFFSourceConfiguration>(ARFFSource::class, ARFFSourceConfiguration::class)
    constructor(block : ARFFSourceConfiguration.() -> Unit) : super(block)

    constructor(configuration: ARFFSourceConfiguration) : this(configuration.asReconfigureBlock())

    override suspend fun produce(): DataBatch<*, *> {
        // Only ever produces the one instance of the ARFF file
        stop()

        return DataBatchView(
            ARFFLoader(configuration.arffLoaderConfiguration).load()
        )
    }

}

class ARFFSourceConfiguration : NodeConfiguration("arffSource") {

    @ConfigurationElement.WithMetadata("The configuration for the ARFF loader")
    var arffLoaderConfiguration by subconfiguration<ARFFLoaderConfiguration>()

}
