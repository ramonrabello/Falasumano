package androidnarede.exemplo.tts;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;

public class MainActivity extends Activity {
	
    private int VOICE_RECOGNITION_REQUEST_CODE = 0;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal_layout);
    }
    
    /**
     * Método que será chamado ao clicar no botão de pesquisa por voz.
     */
    public void pesquisarComVoz(View v) {
    	
    	new Thread(new Runnable(){

			@Override
			public void run() {
				
				// configura Intent para carregar a engine de reconhecimento de voz
				Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
				
				// Identifica o pacote da aplicação
				intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getClass().getPackage().getName());
				
				// Um texto de sugestão para a pesquisa por voz.
				intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Faaaaala sumano: (ex: android)");
				
				// Define o modelo de reconhecimento de voz que será utilizado.
				intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
				
				// configura o número máximo de ocorrências que será 
				// detectado pelo mecanismo de reconhecimento de voz.
				int MAX_OCORRENCIAS = 5;
				
				// configura o número máximo de ocorrências
				intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, MAX_OCORRENCIAS);
				
				// inicia a Activity e comunica o mecanismo de reconhecimento
				// de voz da plataforma Android.
	     		startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);				
			}   		
    	}).start();
    	
    }
    
    /**
     * Chamado quando obtem-se ocorrências de reconhecimento de voz.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       
    	// se foi possível reconhecer alguma coisa
    	if (requestCode == VOICE_RECOGNITION_REQUEST_CODE && resultCode == RESULT_OK) {
            
        	// Preenche a lista com estas ocorrências
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            
            if (!matches.isEmpty()){
            	// recupera a primeira ocorrência (de mais relevância)
            	String valor = matches.get(0);
            	
            	// exibe no mapa as localidades encontradas, de acordo com o filtro vocal
            	Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=" + valor));
            	startActivity(intent);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}