package māia.topology.node.standard.ml.dataset

import māia.configure.Configurable
import māia.configure.ConfigurationElement
import māia.configure.ConfigurationItem
import māia.configure.asReconfigureBlock
import māia.ml.dataset.DataBatch
import māia.ml.dataset.DataStream
import māia.ml.dataset.arff.load
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
class ARFFSource : Source<ARFFSourceConfiguration, DataStream<*>> {

    @Configurable.Register<ARFFSource, ARFFSourceConfiguration>(ARFFSource::class, ARFFSourceConfiguration::class)
    constructor(block : ARFFSourceConfiguration.() -> Unit) : super(block)

    constructor(configuration: ARFFSourceConfiguration) : this(configuration.asReconfigureBlock())

    override suspend fun produce(): DataStream<*> {
        // Only ever produces the one instance of the ARFF file
        stop()

        return load(configuration.filename, configuration.batch)
    }

}

class ARFFSourceConfiguration : NodeConfiguration("arffSource") {

    @ConfigurationElement.WithMetadata("The name of the ARFF file to load")
    var filename by ConfigurationItem { "" }

    @ConfigurationElement.WithMetadata("Whether to batch all rows from the file or stream them")
    var batch by ConfigurationItem { false }

}
