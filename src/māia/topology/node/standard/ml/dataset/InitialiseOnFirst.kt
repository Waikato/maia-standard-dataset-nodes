package māia.topology.node.standard.ml.dataset

import māia.configure.Configurable
import māia.configure.asReconfigureBlock
import māia.ml.dataset.WithColumnHeaders
import māia.ml.dataset.view.readOnlyView
import māia.topology.ExecutionState
import māia.topology.Node
import māia.topology.NodeConfiguration
import māia.topology.io.Output
import māia.topology.io.Throughput
import māia.topology.node.base.LockStepTransformer

/**
 * Utility node which initialises a learner on the first data-dataset
 * to pass through it.
 *
 * @author Corey Sterling (csterlin at waikato dot ac dot nz)
 */
@Node.WithMetadata("Outputs the headers of the first item seen")
class InitialiseOnFirst<D : WithColumnHeaders> : LockStepTransformer<InitialiseOnFirstConfiguration, D, D> {

    @Configurable.Register<InitialiseOnFirst<*>, InitialiseOnFirstConfiguration>(
        InitialiseOnFirst::class, InitialiseOnFirstConfiguration::class)
    constructor(block : InitialiseOnFirstConfiguration.() -> Unit) : super(block)

    constructor(configuration : InitialiseOnFirstConfiguration) : this(configuration.asReconfigureBlock())

    @Throughput.WithMetadata("Outputs the initialisation headers for a learner")
    val initialise by Output<WithColumnHeaders>()

    /** Whether the item is the first seen. */
    private var first by ExecutionState { true }

    override suspend fun transformSingle(item: D): D {
        initialiseOnFirst(item)
        return item
    }

    private suspend fun initialiseOnFirst(item : D) {
        if (first) {
            doAsync {
                initialise.push(item.readOnlyView())
                initialise.close()
            }
            first = false
        }
    }

}

class InitialiseOnFirstConfiguration : NodeConfiguration("initialiseOnFirst")
