/*
 * © 2010, Jakub Valenta
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * Neither the name of the Jakub Valenta
 * nor the names of its contributors may be used to endorse or promote products
 * derived from this software without specific prior written permission.
 *
 * This software is provided by the copyright holders and contributors “as is” and any
 * express or implied warranties, including, but not limited to, the implied
 * warranties of merchantability and fitness for a particular purpose are disclaimed.
 * In no event shall the foundation or contributors be liable for any direct, indirect,
 * incidental, special, exemplary, or consequential damages (including, but not limited to,
 * procurement of substitute goods or services; loss of use, data, or profits; or business
 * interruption) however caused and on any theory of liability, whether in contract, strict
 * liability, or tort (including negligence or otherwise) arising in any way out of the use
 * of this software, even if advised of the possibility of such damage.
 */

package cz.cvut.fel.mvod.persistence;

import cz.cvut.fel.mvod.common.Question;
import cz.cvut.fel.mvod.common.Vote;
import cz.cvut.fel.mvod.common.Voter;
import cz.cvut.fel.mvod.common.Voting;
import cz.cvut.fel.mvod.crypto.CryptoUtils;
import cz.cvut.fel.mvod.persistence.derby.DerbyDAOFactory;
import cz.cvut.fel.mvod.persistence.derby.DerbyDAOFactoryImpl;
import cz.cvut.fel.mvod.persistence.regsys.RegSysDAO;
import cz.cvut.fel.mvod.persistence.regsys.commonInterface.entities.Action;
import cz.cvut.fel.mvod.persistence.regsys.commonInterface.entities.Participant;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author jakub
 */
public class DAOFacadeImpl implements DAOFacade {

	private static DAOFacade instance;

	private final VotingDAO votings;
	private final VoterDAO voters;
	private final VoteDAO votes;
        private final RegSysDAO regSys;
	private final QuestionDAO questionsDB;
	private final VotingDAO votingsDB;
	private final VoterDAO votersDB;
	private final VoteDAO votesDB;
	private Voting currentVoting = null;

	private DAOFacadeImpl() throws DAOException {
		votings = DAOFactoryImpl.getInstance().getVotingDAO();
		voters = DAOFactoryImpl.getInstance().getVoterDAO();
		votes = DAOFactoryImpl.getInstance().getVoteDAO();
                regSys = DAOFactoryImpl.getInstance().getRegSysDAO();
		DerbyDAOFactory factory = new DerbyDAOFactoryImpl();
		questionsDB = factory.getQuestionDAO();
		votingsDB = factory.getVotingDAO();
		votersDB = factory.getVoterDAO();
		votesDB = factory.geVoteDAO();
                
	}

	public static synchronized void initInstance() throws DAOException {
		if(instance == null) {
			instance = new DAOFacadeImpl();
		}
	}

	public static DAOFacade getInstance() {
		if(instance == null) {
			throw new IllegalAccessError("Unitialized singleton.");
		}
		return instance;
	}

	/**
	 * {@inheritDoc }
	 */
	public Question getQuestion(int id) throws DAOException {
		for(Voting voting: votings.getVotings()) {
			for(Question q: voting.getQuestions()) {
				if(q.getId() == id) {
					return q;
				}
			}
		}
		return null;
	}

	/**
	 * {@inheritDoc }
	 */
	public Voter getVoter(String userName) throws DAOException {
		for(Voter voter: voters.retrieveVoters()) {
			if(voter.getUserName().equals(userName)) {
				return voter;
			}
		}
		return null;
	}

	public Voting getCurrentVoting() {
		return currentVoting;
	}

	public void setCurrentVoting(Voting voting) throws DAOException {
		currentVoting = voting;
		votings.saveVoting(currentVoting, null);
	}

	public void saveCurrentVoting() throws DAOException {
		if(currentVoting != null) {
			synchronized(votersDB) {
				for(Voter voter: voters.retrieveVoters()) {
					if(voter.getId() == -1) {
						Voter v = votersDB.getVoter(voter.getUserName());
						if(v == null) {
							votersDB.saveVoter(voter);
						} else {
							voter.setId(v.getId());
							votersDB.updateVoter(voter);
						}
					} else {
						votersDB.updateVoter(voter);
					}
				}
			}
			synchronized(votingsDB) {
				if(currentVoting.getId() == -1) {
					votingsDB.saveVoting(currentVoting, voters.retrieveVoters());
				}
			}
			synchronized(questionsDB) {
				for(Question question: currentVoting.getQuestions()) {
					if(question.getId() == -1 && (question.getState() == Question.State.RUNNING ||
							question.getState() == Question.State.FINISHED)) {
						questionsDB.saveQuestion(question, currentVoting.getId());
					}
				}
			}
		}
		votings.updateVoting(currentVoting);
	}

	public void notifyVotingChanged() throws DAOException {
		saveCurrentVoting();
	}

	public void saveVote(Vote vote) throws DAOException {
		votes.saveVote(vote);
		synchronized(votesDB) {
			votesDB.saveVote(vote);
		}
	}

	@Override
	public void retrieveVotersFromDatabase() throws DAOException {
		synchronized(votersDB) {
			for(Voter voter: votersDB.retrieveVoters()) {
				voters.saveVoter(voter);
			}
		}
	}
        
        @Override
	public void retrieveVotersFromRegSys() throws DAOException {
		synchronized(regSys) {
                    
                    
                    ArrayList<Participant> al = regSys.getParticipants();
                    System.out.println("I got "+al.size()+" participants");
                    Iterator it = al.iterator();
                    
                    // wipe voters
                    ArrayList<Voter> al2 = new ArrayList(voters.retrieveVoters());
                    Iterator<Voter> it2 = al2.iterator();
                    while(it2.hasNext()) {
                        voters.deleteVoter(it2.next());
                    }
                    
                    while(it.hasNext()) {
                        Participant p = (Participant) it.next();
                        voters.saveVoter(new Voter(p.getFirstName(),p.getLastName(),CryptoUtils.passwordDigest(p.getPassword(), p.getLogin()),p.getLogin()));
                    }
                    //RegSysAction a = (RegSysAction) action;
//                    System.out.println("Called with id:"+action.getId());
//                    for(int i = 0; i < 10; i++) {
//                        voters.saveVoter(new Voter("Pepa", "Kobza", new String("jeb").getBytes(), "pepakobza"+i));
//                    }
		}
	}

}
