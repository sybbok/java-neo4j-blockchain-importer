package tech.vng.bitcoin.blockimporter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bitcoinj.core.Block;
import org.bitcoinj.core.Coin;
import org.bitcoinj.core.Context;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.TransactionInput;
import org.bitcoinj.core.TransactionOutput;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.utils.BlockFileLoader;

public class BlockchainNeo4JImporter 
{
	private static final Log log = LogFactory.getLog(BlockchainNeo4JImporter.class);

	
    public static void main( String[] args )
    {
    	List<File>  blockChainFiles = getFilesFromDir("/media/vngan/7fd00e8d-2f41-411e-9fcc-e3a1543118e0/bitcoin-blockchain/blocks/");
    	BlockFileLoader bfl = new BlockFileLoader(new MainNetParams(), blockChainFiles);
    	Context.getOrCreate(MainNetParams.get());
    	int debugCounter=0;

    	for (Block block : bfl) {
    		log.info(block.toString());
    		
    		if(debugCounter++>10) {
    			break;
    		}
    		
    	    List<Transaction> transactions = block.getTransactions();
    	    for (Transaction t : transactions) {
    	    	List<TransactionInput> inputs = t.getInputs();
    	    	for (TransactionInput transactionInput : inputs) {
    	    		Coin coin = transactionInput.getValue();
    	    		if(coin != null) {
    	    			long value = coin.getValue();
    	    			log.info("value="+ value);
    	    		} else {
    	    			log.info("coin is null");
    	    		}
				}
    	    	List<TransactionOutput> outputs = t.getOutputs();
    	    	for (TransactionOutput transactionOutput : outputs) {
					Coin coin = transactionOutput.getValue();
					if(coin != null) {
						long value = coin.getValue();
						log.info("value="+ value);
	    			} else {
	    				log.info("coin is null");
	    			}
				}
			}
    	    log.info("block hash= "+ block.getHash());

    	}

    }

	private static List<File> getFilesFromDir(String dir) {
		List<File> blockChainFiles = new ArrayList<>();
    	blockChainFiles.add(new File("/media/vngan/7fd00e8d-2f41-411e-9fcc-e3a1543118e0/bitcoin-blockchain/blocks/blk00285.dat"));
    	return blockChainFiles;
	}
}
